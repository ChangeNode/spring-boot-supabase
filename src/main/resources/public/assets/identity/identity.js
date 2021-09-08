const SUPABASE_URL = '@supabase.url@';
const SUPABASE_ANON_KEY = '@supabase.anon.key@';

window.userToken = null;
window.isSignUpPage = false;
const urlParams = new URLSearchParams(window.location.search);

var supabase = supabase.createClient(SUPABASE_URL, SUPABASE_ANON_KEY);
supabase.auth.onAuthStateChange((event, session) => {
    //console.log(event, session);
    setToken(session);
    if (isSignUpPage) {
        if (session.access_token) {
            window.location.replace("@supabase.server@");
        }
    }

    if (event === 'PASSWORD_RECOVERY')
        window.location.replace("@supabase.server@public/update-password");
})

document.addEventListener('DOMContentLoaded', function (event) {
    const logoutButton = document.querySelector('#logout-button');
    if (logoutButton)
        logoutButton.onclick = logoutSubmitted.bind(logoutButton)

    if (urlParams.get('error_description'))
        displayError(urlParams.get('error_description'));
})

const logoutSubmitted = (event) => {
    event.preventDefault()
    supabase.auth.signOut();
}

function displayError(errorMessage) {
    const toastElement = document.getElementById('toast');
    const toastBody = document.getElementById('toast-body')
    if (!toastElement || !toastBody) {
        console.log("Can't find toast or toast-body");
        console.log(errorMessage);
        alert(errorMessage);
        return;
    }

    toastBody.innerHTML = errorMessage;
    const toast = new bootstrap.Toast(toastElement)
    toast.show()
}

function updateCookies() {
    if (!supabase || !supabase.auth || !supabase.auth.session()) {
        document.cookie = "access-token=null;max-age=0;path=/";
        return;
    }

    if (localStorage.getItem("remember-me") === 'true')
        document.cookie = "access-token=" + supabase.auth.session().access_token + ";max-age=2147483640;path=/";
    else
        document.cookie = "access-token=" + supabase.auth.session().access_token + ";path=/";
}

function setToken(response) {
    if (!response || !response.user) {
        document.cookie = "access-token=null;max-age=0;path=/";
        return;
    }

    if (response.user.confirmation_sent_at && !response.access_token) {
        displayError('Confirmation Email Sent')
    } else {
        updateCookies(response.access_token);
    }
}

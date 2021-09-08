package com.changenode.frisson;

import com.changenode.frisson.data.ToDo;
import com.changenode.frisson.query.TodosEntityQuery;
import com.changenode.frisson.query.UserQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMockMvc(addFilters = false)
class DataAccessTests {

    @Autowired
    TodosEntityQuery todosEntityQuery;

    @Autowired
    UserQuery userQuery;

    @Test
    public void insertAndDelete() {
        ToDo toDo = new ToDo();
        toDo.setTask("Hello");
        toDo.setInsertedAt(Instant.now());
        toDo.setUserId(UUID.fromString("517d99fe-db7d-467e-b3f2-be5ac8c41752"));
        todosEntityQuery.save(toDo);
        assertThat(toDo.getId()).isNotNull();
    }
}

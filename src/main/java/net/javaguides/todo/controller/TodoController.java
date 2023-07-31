package net.javaguides.todo.controller;

import net.javaguides.todo.dto.TodoDto;
import net.javaguides.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){
        TodoDto todoDtoSaved = todoService.addTodo(todoDto);
        return new ResponseEntity<>(todoDtoSaved, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long id){
        TodoDto todoDto = todoService.getTodo(id);
        return new ResponseEntity<>(todoDto,HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getTodos(){
        List<TodoDto> todoDtoList = todoService.getAllTodos();
        return ResponseEntity.ok(todoDtoList);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> getTodos(@RequestBody TodoDto todoDto,@PathVariable Long id){
        TodoDto todoDtoUpdated = todoService.updateTodo(todoDto,id);
        return ResponseEntity.ok(todoDtoUpdated);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
        return new ResponseEntity<>("todo deletes successfully !",HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("/complete/{id}")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable Long id){
        TodoDto todoDtoUpdated = todoService.completeTodo(id);
        return new ResponseEntity<>( todoDtoUpdated,HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("/incomplete/{id}")
    public ResponseEntity<TodoDto> incompleteTodo(@PathVariable Long id){
        TodoDto todoDtoUpdated = todoService.incompleteTodo(id);
        return new ResponseEntity<>( todoDtoUpdated,HttpStatus.OK);
    }
}

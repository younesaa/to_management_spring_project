package net.javaguides.todo.service.impl;

import net.javaguides.todo.dto.TodoDto;
import net.javaguides.todo.entity.Todo;
import net.javaguides.todo.exception.ResourceNotfoundException;
import net.javaguides.todo.exception.SavingEntityException;
import net.javaguides.todo.repository.TodoRepository;
import net.javaguides.todo.service.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public TodoDto addTodo(TodoDto todoDto){
        Todo todo = modelMapper.map(todoDto,Todo.class);
        Todo savedTodo = todoRepository.save(todo);
        if(savedTodo == null ) new SavingEntityException("entity not saved");
        TodoDto savedTodoDto = modelMapper.map(savedTodo,TodoDto.class);
        return savedTodoDto;
    }

    @Override
    public TodoDto getTodo(Long id) {

        Todo todo = todoRepository.findById(id).orElseThrow( () -> new ResourceNotfoundException("resource Not found with : " + id));

        return modelMapper.map(todo,TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> todosList = todoRepository.findAll();

        return todosList.stream().map((todo) -> modelMapper.map(todo,TodoDto.class)).collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto,Long id) {
        Todo todo= todoRepository.findById(id).orElseThrow( () -> new ResourceNotfoundException("resource Not found with : " + id));
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todo.isCompleted());
        Todo updatedTodo = todoRepository.save(todo);
        if(updatedTodo == null ) new SavingEntityException("entity not saved");
        TodoDto updatedTodoDto = modelMapper.map(updatedTodo,TodoDto.class);
        return updatedTodoDto;
    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotfoundException("resource not found with id : " + id));
        todoRepository.deleteById(id);
    }

    @Override
    public TodoDto completeTodo(Long id) {
        Todo todo= todoRepository.findById(id).orElseThrow( () -> new ResourceNotfoundException("resource Not found with : " + id));
        todo.setCompleted(Boolean.TRUE);
        Todo updatedTodo = todoRepository.save(todo);
        if(updatedTodo == null ) new SavingEntityException("entity not saved");
        TodoDto updatedTodoDto = modelMapper.map(updatedTodo,TodoDto.class);
        return updatedTodoDto;
    }
    @Override
    public TodoDto incompleteTodo(Long id) {
        Todo todo= todoRepository.findById(id).orElseThrow( () -> new ResourceNotfoundException("resource Not found with : " + id));
        todo.setCompleted(Boolean.FALSE);
        Todo updatedTodo = todoRepository.save(todo);
        if(updatedTodo == null ) new SavingEntityException("entity not saved");
        TodoDto updatedTodoDto = modelMapper.map(updatedTodo,TodoDto.class);
        return updatedTodoDto;
    }
}

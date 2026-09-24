package api_teste.ds.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api_teste.ds.models.Task;
import api_teste.ds.models.User;
import api_teste.ds.repositories.TaskRepository;

@Service
public class TaskService {

    @Autowired 
    private TaskRepository taskRepository;
    
    @Autowired 
    private UserService userService;

    @Transactional(readOnly = true)
    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new RuntimeException(
            "Tarefa não encontrada! Id: " + id + ", Tipo: " + Task.class.getName()
        ));
    }   

    @Transactional(readOnly = true)
    public List<Task> findAllByUserId(Long userId) {
        // Valida se o usuário existe antes de buscar as tarefas
        this.userService.findById(userId);
        return this.taskRepository.findByUser_Id(userId);
    }

    @Transactional
    public Task create(Task obj) {
        if (obj.getUser() == null || obj.getUser().getId() == null) {
            throw new RuntimeException("A tarefa precisa estar vinculada a um usuário válido.");
        }

        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        return this.taskRepository.save(obj);
    }

    @Transactional
    public Task update(Task obj) {
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir a tarefa pois existem entidades relacionadas a ela.");
        }
    }
}
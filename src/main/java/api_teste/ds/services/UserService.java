package api_teste.ds.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api_teste.ds.models.User;
import api_teste.ds.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException(
            "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()
        ));
    }

    @Transactional
    public User create(User obj) {
        obj.setId(null);
        return this.userRepository.save(obj);
    }

    @Transactional
    public User update(User obj) {
        User newObj = findById(obj.getId());
        
        // Ajuste os setters abaixo de acordo com os atributos reais da sua classe User
        // Exemplo: newObj.setPassword(obj.getPassword());
        newObj.setDescription(obj.getDescription()); 

        return this.userRepository.save(newObj);
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir o usuário pois existem tarefas vinculadas a ele.");
        }
    }
}
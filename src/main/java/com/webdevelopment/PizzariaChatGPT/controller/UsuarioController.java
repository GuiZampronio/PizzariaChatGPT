import com.webdevelopment.PizzariaChatGPT.dto.Usuario;
import com.webdevelopment.PizzariaChatGPT.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

        @Autowired
        private UsuarioRepository usuarioRepository;

        @PostMapping
        public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
                Usuario novoUsuario = usuarioRepository.save(usuario);
                return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable long id) {
                Usuario usuario = usuarioRepository.findById(id).orElse(null);
                if (usuario == null) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(usuario, HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Usuario> atualizarUsuario(@PathVariable long id, @RequestBody Usuario usuarioAtualizado) {
                Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
                if (usuarioExistente == null) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                usuarioExistente.setNome(usuarioAtualizado.getNome());
                usuarioExistente.setEndereco(usuarioAtualizado.getEndereco());
                usuarioExistente.setContato(usuarioAtualizado.getContato());
                Usuario usuarioAtualizadoSalvo = usuarioRepository.save(usuarioExistente);
                return new ResponseEntity<>(usuarioAtualizadoSalvo, HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<HttpStatus> excluirUsuario(@PathVariable long id) {
                Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
                if (usuarioExistente == null) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                usuarioRepository.delete(usuarioExistente);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
}
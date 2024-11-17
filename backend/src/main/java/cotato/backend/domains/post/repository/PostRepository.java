package cotato.backend.domains.post.repository;

import cotato.backend.domains.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

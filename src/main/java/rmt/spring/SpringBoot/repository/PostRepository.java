package rmt.spring.SpringBoot.repository;

import org.springframework.data.repository.CrudRepository;
import rmt.spring.SpringBoot.entity.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
}

package rmt.spring.SpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rmt.spring.SpringBoot.entity.Post;
import rmt.spring.SpringBoot.repository.PostRepository;

import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blog(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String preview,
                              @RequestParam String text,
                              Model model) {
        Post post = new Post(title, preview, text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogPostDetails(@PathVariable(value = "id") long id,
                                  Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        model.addAttribute("post", post.get());
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogPostEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        model.addAttribute("post", post.get());
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id,
                                 @RequestParam String title,
                                 @RequestParam String preview,
                                 @RequestParam String text,
                                 Model model) {
        Post post = postRepository.findById(id).orElseThrow(IllegalStateException::new);
        post.setTitle(title);
        post.setPreview(preview);
        post.setText(text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow(IllegalStateException::new);
        postRepository.delete(post);
        return "redirect:/blog";
    }
}

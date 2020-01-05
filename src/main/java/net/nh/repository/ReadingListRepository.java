package net.nh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.nh.domain.Book;

public interface ReadingListRepository extends JpaRepository<Book, Long> {

    List<Book> findByReader(String reader);

}

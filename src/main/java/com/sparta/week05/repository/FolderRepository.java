package com.sparta.week05.repository;

import com.sparta.week05.models.Folder;
import com.sparta.week05.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findAllByUser(User user);
    List<Folder> findAllByUserAndNameIn(User user, List<String> nameList);
    Folder findByName(String folserName);
//    Optional<Folder> findAllByFolderName(String folderName);
}

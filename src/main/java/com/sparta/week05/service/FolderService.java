package com.sparta.week05.service;

import com.sparta.week05.exception.ApiRequestException;
import com.sparta.week05.models.Folder;
import com.sparta.week05.models.Product;
import com.sparta.week05.models.User;
import com.sparta.week05.repository.FolderRepository;
import com.sparta.week05.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FolderService {
    // 멤버 변수 선언
    private final FolderRepository folderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public FolderService(FolderRepository folderRepository, ProductRepository productRepository) {
        // 멤버 변수 생성
        this.folderRepository = folderRepository;
        this.productRepository = productRepository;
    }

    // 회원 ID로 등록된 모든 폴더 조회
    public List<Folder> getFolders(User user) {
        return folderRepository.findAllByUser(user);
    }

    @Transactional(readOnly = false)
    public List<Folder> createFolders(List<String> folderNameList, User user) {
        List<Folder> folderList = new ArrayList<>();

        for (String folderName : folderNameList) {
            // 1) DB에 폴더면ㅇ이 folderName인 폴더가 존재하는지?
            Folder folderInDB = folderRepository.findByName(folderName);

            if (folderInDB != null) {
                // DB에 중복 폭더명 존재한다면 Exception 발생시킴
                throw new ApiRequestException("중복된 폴더명 (" + folderName + ")을 삭제하고 재시도해 주세요!");
            }

            // 2) 폴더를 DB에 저장
            Folder folder = new Folder(folderName, user);
            folder = folderRepository.save(folder);

            // 3) folderList에 folder Entity 객체를 추가
            folderList.add(folder);
        }

        return folderList;
    }

//    public List<Folder> createFolders(List<String> folderNameList, User user) {
//        List<Folder> folderList = new ArrayList<>();
//        for (String folderName : folderNameList) {
//            // 1) DB에 폴더명이 folderName인 폴더가 좋재하는지?
//            Folder folderInDB = folderRepository.findByName(folderName);
//            if (folderInDB != null) {
//                // 그동안 저장된 폴더들을 모두 삭제
//                for (Folder folder : folderList) {
//                    folderRepository.delete(folder);
//                }
//
//                // DB에 중복 폴더명 존재한다면 Exception 발생시킴
//                throw new IllegalArgumentException("중복된 폴더명(" + folderName + ")ㅇ르 삭제하고 재시도해 주세요!");
//            }
//
//            // 2) 폴더를 DB에 저장
//            Folder folder = new Folder(folderName, user);
//            folder = folderRepository.save(folder);
//
//            // 3) folderList에 folder Entity 객체를 추가
//            folderList.add(folder);
//        }
//        return folderList;
//    }

    public boolean isExistFolderName(String folderName, List<Folder> existFolderList) {
        // 기존 폴더 리스트에서 folder name이 있는지?
        for (Folder exisFolder : existFolderList) {
            if (exisFolder.getName().equals(folderName)){
                return true;
            }
        }

        return false;
    }

    // 회원 ID가 소유한 폴더에 저장되어 있는 상품들 조회
    public Page<Product> getProductsOnFolder(User user, int page, int size, String sortBy, boolean isAsc, Long folderId) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAllByUserIdAndFolderList_Id(user.getId(), folderId, pageable);
    }
}

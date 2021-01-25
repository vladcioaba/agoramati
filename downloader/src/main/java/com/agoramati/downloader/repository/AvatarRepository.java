package com.agoramati.downloader.repository;

import com.agoramati.downloader.model.AvatarData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends JpaRepository<AvatarData, Long> {

    @Query("SELECT s FROM AvatarData s WHERE s.avatarId = ?1")
    public AvatarData getAvatarData(String avatarId);
}
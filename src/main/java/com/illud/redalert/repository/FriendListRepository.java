package com.illud.redalert.repository;

import com.illud.redalert.domain.FriendList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FriendList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FriendListRepository extends JpaRepository<FriendList, Long> {

}

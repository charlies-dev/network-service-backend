package com.infy.network.repository;

import com.infy.network.entity.ConnectionGroupMember;
import com.infy.network.entity.ConnectionGroupMemberId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionGroupMemberRepository extends JpaRepository<ConnectionGroupMember, ConnectionGroupMemberId> {

    List<ConnectionGroupMember> findByGroupId(Long groupId);

    void deleteByGroupIdAndConnectedUserIdIn(Long groupId, List<Long> userIds);
}

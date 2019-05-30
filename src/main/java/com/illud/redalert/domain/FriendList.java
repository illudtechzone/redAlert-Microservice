package com.illud.redalert.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A FriendList.
 */
@Entity
@Table(name = "friend_list")
public class FriendList implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "friend_id")
    private String friendId;

    @ManyToOne
    @JsonIgnoreProperties("friends")
    private UserDetails userDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFriendId() {
        return friendId;
    }

    public FriendList friendId(String friendId) {
        this.friendId = friendId;
        return this;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public FriendList userDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
        return this;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FriendList friendList = (FriendList) o;
        if (friendList.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), friendList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FriendList{" +
            "id=" + getId() +
            ", friendId='" + getFriendId() + "'" +
            "}";
    }
}

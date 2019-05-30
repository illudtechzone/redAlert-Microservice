package com.illud.redalert.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the FriendList entity.
 */
public class FriendListDTO implements Serializable {

    private Long id;

    private String friendId;


    private Long userDetailsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public Long getUserDetailsId() {
        return userDetailsId;
    }

    public void setUserDetailsId(Long userDetailsId) {
        this.userDetailsId = userDetailsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FriendListDTO friendListDTO = (FriendListDTO) o;
        if (friendListDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), friendListDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FriendListDTO{" +
            "id=" + getId() +
            ", friendId='" + getFriendId() + "'" +
            ", userDetails=" + getUserDetailsId() +
            "}";
    }
}

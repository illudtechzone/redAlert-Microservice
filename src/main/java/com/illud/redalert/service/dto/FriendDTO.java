package com.illud.redalert.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.illud.redalert.domain.enumeration.Status;

/**
 * A DTO for the Friend entity.
 */
public class FriendDTO implements Serializable {

    private Long id;

    private String userId;

    private String friendId;

    private Boolean accepted;

    private Status status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public Boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FriendDTO friendDTO = (FriendDTO) o;
        if (friendDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), friendDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FriendDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", friendId='" + getFriendId() + "'" +
            ", accepted='" + isAccepted() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}

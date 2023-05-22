package ru.vsu.cs.chirk.entity.DTO.requestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestEstimateDTO {
    private long idUser;
    private long idChirk;
    private Boolean isLiked;

    @Override
    public String toString() {
        return "RequestEstimateDTO{" +
                "idUser=" + idUser +
                ", idChirk=" + idChirk +
                ", isLiked=" + isLiked +
                '}';
    }
}

package tn.esprit.pidev.models;
import java.time.LocalDateTime;

public class Reponse {
    private int id;
    private String description;
    private int reclamationId;
    private LocalDateTime createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReclamationId() {
        return reclamationId;
    }

    public void setReclamationId(int reclamationId) {
        this.reclamationId = reclamationId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", reclamationId=" + reclamationId +
                ", createdAt=" + createdAt +
                '}';
    }
}
package pl.beng.thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.beng.thesis.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}

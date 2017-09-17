package pl.beng.thesis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.beng.thesis.model.Notification;
import pl.beng.thesis.repository.NotificationRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public Notification find(Long id) {
        return notificationRepository.findOne(id);
    }

    @Transactional
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Transactional
    public Notification update(Notification updatedNotification) {
        return notificationRepository.saveAndFlush(updatedNotification);
    }
}

package pl.beng.thesis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.beng.thesis.model.Notification;

import pl.beng.thesis.repository.NotificationRepository;

import javax.annotation.security.DenyAll;
import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DeveloperService developerService;

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository,
                               DeveloperService developerService) {

        this.notificationRepository = notificationRepository;
        this.developerService = developerService;
    }

    /**
     * Find one notification by given id
     * @param id notification's id
     * @return notification with given id
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public Notification find(Long id) {
        return notificationRepository.findOne(id);
    }

    /**
     * Receive all notification from database
     * @return notification list
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    /**
     * Update existing notification
     * @param updatedNotification notification to update
     * @return updatedNotification
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HR')")
    public Notification update(Notification updatedNotification) {
        return notificationRepository.saveAndFlush(updatedNotification);
    }

    /**
     * Create new notification
     * @param newNotification new notification to persist
     * @return created notification
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HR')")
    public Notification createNotification(Notification newNotification) {
        return notificationRepository.saveAndFlush(newNotification);
    }

    /**
     * Method responsible for creating new notifications
     * for employee which haven't updated their skill, project
     * in past year.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @Scheduled(cron = "${cron.expression}")
    @DenyAll
    public void scheduledNotificationUpdate() {
        logger.info("START NOTIFICATION UPDATE!");
        developerService.findAll().forEach(developer -> {
            /* Check if last modification date is year before current date */
            if(LocalDate.now().minusYears(1).isBefore(developer.getLastModificationDate())) {

                /* If last notification date was created in past 7 days don't do it again */
                if(developer.getLastNotificationDate() == null ||
                        developer.getLastNotificationDate().isBefore(LocalDate.now().minusDays(7))) {

                    /* Create new notification */
                    Notification notification = new Notification("It's been year since your last data update!", developer);
                    developer.getNotifications().add(notification);

                    /* Log notification */
                    logger.info("Created new notification: " + notification);

                    /* Set last notification date to prevent new notifications */
                    developer.setLastNotificationDate(LocalDate.now());

                    /* Persist new notification */
                    createNotification(notification);
                    developerService.updateDeveloper(developer);
                }
            }
        });
    }
}

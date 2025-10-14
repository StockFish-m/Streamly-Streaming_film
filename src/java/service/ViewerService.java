package service;

import dao.UserDAO;
import dao.UserDAOImpl;
import dto.ViewerDTO;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import model.subscription.ViewerSubscription;
import model.user.Viewer;

/**
 * Service layer that converts {@link Viewer} entities to {@link ViewerDTO}
 * objects (and vice versa) while keeping subscription information consistent.
 * The previous implementation was commented-out and contained several logic
 * bugs such as calling setter methods instead of getters and attempting to use
 * non-existent types. This class provides a clean, testable implementation that
 * can be safely consumed by servlets or other services.
 */
public class ViewerService {

    private final UserDAO userDAO;

    public ViewerService() {
        this(new UserDAOImpl());
    }

    public ViewerService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Convert a {@link Viewer} entity to its DTO representation. Subscription
     * dates are converted from {@link LocalDate} to {@link Date} so they remain
     * compatible with existing JSP views.
     */
    public ViewerDTO toDTO(Viewer viewer) {
        if (viewer == null) {
            return null;
        }

        ViewerDTO dto = new ViewerDTO();
        dto.setId(String.valueOf(viewer.getUser_id()));
        dto.setUsername(viewer.getUsername());
        dto.setEmail(viewer.getEmail());
        dto.setCreatedAt(viewer.getCreated_at());

        ViewerSubscription sub = viewer.getActiveSubscription();
        if (sub != null) {
            dto.setActiveSubscriptionName(sub.getPlanName());
            dto.setSubscriptionStartDate(toDate(sub.getPurchaseDate()));
            dto.setSubscriptionEndDate(toDate(sub.getExpiryDate()));
        }

        return dto;
    }

    /**
     * Convert a {@link ViewerDTO} back to a {@link Viewer} entity. This keeps
     * the mapping symmetrical with {@link #toDTO(Viewer)} and allows the service
     * to be reused for both read and write operations.
     */
    public Viewer fromDTO(ViewerDTO dto) {
        if (dto == null) {
            return null;
        }

        Viewer viewer = new Viewer();

        if (dto.getId() != null && !dto.getId().isBlank()) {
            try {
                viewer.setUser_id(Integer.parseInt(dto.getId()));
            } catch (NumberFormatException ignored) {
                // Leave id unset when it cannot be parsed. This gracefully
                // handles DTOs built from user input.
            }
        }

        viewer.setUsername(dto.getUsername());
        viewer.setEmail(dto.getEmail());
        viewer.setCreated_at(dto.getCreatedAt());

        if (dto.getActiveSubscriptionName() != null
                || dto.getSubscriptionStartDate() != null
                || dto.getSubscriptionEndDate() != null) {
            ViewerSubscription subscription = new ViewerSubscription();
            subscription.setPlanName(dto.getActiveSubscriptionName());
            subscription.setPurchaseDate(toLocalDate(dto.getSubscriptionStartDate()));
            subscription.setExpiryDate(toLocalDate(dto.getSubscriptionEndDate()));
            if (viewer.getUser_id() > 0) {
                subscription.setViewerId(viewer.getUser_id());
            }
            viewer.setActiveSubscription(subscription);
        }

        return viewer;
    }

    public ViewerDTO getViewerDTOById(int id) {
        Viewer viewer = userDAO.getViewer(id);
        return toDTO(viewer);
    }

    public ViewerDTO getViewerDTOById(String id) {
        if (id == null || id.isBlank()) {
            return null;
        }
        try {
            return getViewerDTOById(Integer.parseInt(id));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public void saveViewerDTO(ViewerDTO dto) {
        Viewer viewer = fromDTO(dto);
        if (viewer == null) {
            return;
        }

        if (viewer.getUser_id() > 0) {
            userDAO.updateViewer(viewer);
        } else {
            userDAO.addViewer(viewer);
        }
    }

    private static Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}


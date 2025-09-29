///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package service;
//
//import dto.ViewerDTO;
//import model.subscription.ViewerSubscription;
//import model.user.Viewer;
//

//public class ViewerService {
//    
//
//    // Convert Entity → DTO
//    public ViewerDTO toDTO(Viewer viewer) {
//        if (viewer == null) return null;
//
//        ViewerSubscription sub = viewer.getActiveSubscription();
//
//        return new ViewerDTO(
//            viewer.getUser_id(),
//            viewer.getUsername(),
//            viewer.getEmail(),
//            viewer.getCreated_at(),
//            sub != null ? sub.setUsername() : null,
//            sub != null ? sub.getStartDate() : null,
//            sub != null ? sub.getEndDate() : null
//        );
//    }
//
//    // Convert DTO → Entity
//    public Viewer fromDTO(ViewerDTO dto) {
//        if (dto == null) return null;
//
//        Subscription sub = null;
//        if (dto.getActiveSubscriptionName() != null) {
//            sub = new Subscription();
//            sub.setName(dto.getActiveSubscriptionName());
//            sub.setStartDate(dto.getSubscriptionStartDate());
//            sub.setEndDate(dto.getSubscriptionEndDate());
//        }
//
//        Viewer viewer = new Viewer();
//        viewer.setId(dto.getId());
//        viewer.setUsername(dto.getUsername());
//        viewer.setEmail(dto.getEmail());
//        viewer.setCreatedAt(dto.getCreatedAt());
//        viewer.setSubscription(sub);
//
//        return viewer;
//    }
//
//    // Get ViewerDTO by ID
//    public ViewerDTO getViewerDTOById(String id) {
//        Viewer viewer = viewerDAO.getViewerById(id);
//        return toDTO(viewer);
//    }
//
//    // Save Viewer from DTO
//    public void saveViewerDTO(ViewerDTO dto) {
//        Viewer viewer = fromDTO(dto);
//        viewerDAO.saveViewer(viewer);
//    }
//}

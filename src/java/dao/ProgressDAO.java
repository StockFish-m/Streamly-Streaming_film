/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


public interface ProgressDAO {
    void saveProgress(int userId, int contentId, double seconds);
    double getProgress(int userId, int contentId);
}

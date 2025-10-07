/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.user.ForgetPasswordToken;


public interface ForgetPasswordTokenDAO {
    
    public boolean insertTokenForget(ForgetPasswordToken tokenForget);
     
    public ForgetPasswordToken getTokenPassword(String token);

    public void updateStatus(ForgetPasswordToken token);
    
    
    
            
}

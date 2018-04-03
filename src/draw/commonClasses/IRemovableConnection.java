/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.commonClasses;

//ovaj interfejs treba da implementiraju sve konekcije koje mogu da budu uklonjene, bilo da se one brisu
//bilo da se brise neki objekat za koji su vezane
//IZ RAZLOGA STO JHOTDRAW ne poziva handleDisconnect u tim slucajevima (potpuno neocekivano ponasanje)
public interface IRemovableConnection {
    //zadatak je da pozove handleDisconnect funkciju
    public void fireHandleDisconnect();
}

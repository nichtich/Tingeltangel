/*
    Copyright (C) 2015   Martin Dames <martin@bastionbytes.de>
  
    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
  
*/

package tingeltangel.core.scripting;

import java.util.Iterator;
import java.util.LinkedList;
import tingeltangel.core.Book;
import tingeltangel.core.Entry;
import tingeltangel.core.MP3Player;


public class Emulator {
    
    private final LinkedList<RegisterListener> listeners = new LinkedList<RegisterListener>();
    
    public final static int REGISTERS = 99;
    
    private final static int[] register = new int[REGISTERS];
    private final static String[] hints = new String[REGISTERS];
    
    static {
        for(int i = 0; i < hints.length; i++) {
            hints[i] = "";
        }
    }
    
    private int leftValue = 0;
    private int rightValue = 0;
    private final Book book;
    
    public Emulator(Book book) {
        this.book = book;
    }
    
    public int getLeftValue() {
        return(leftValue);
    }
    
    public int getRightValue() {
        return(rightValue);
    }
    
    public void setHint(int i, String hint) {
        hints[i] = hint;
    }
    
    public String getHint(int i) {
        return(hints[i]);
    }
    
    public void setLeftValue(int value) {
        leftValue = value;
    }
    
    public void setRightValue(int value) {
        rightValue = value;
    }
    
    public int getMaxRegister() {
        return(REGISTERS - 1);
    }
    
    public int getRegister(int i) {
        return(register[i]);
    }
    
    public void setRegister(int i, int value) {
        int oval = register[i];
        register[i] = value;
        if(oval != value) {
            Iterator<RegisterListener> it = listeners.iterator();
            while(it.hasNext()) {
                it.next().registerChanged(i, value);
            }
        }
        register[i] = value;
    }
    
    public void play(int oid) {
        Entry entry = book.getEntryFromTingID(oid);
        if(entry.isMP3() && (entry.getMP3() != null)) {
            String hint = Integer.toString(oid);
            String indexHint = entry.getHint();
            if(!indexHint.isEmpty()) {
                hint += " (" + indexHint + ")";
            }
            MP3Player.getPlayer().add(entry.getMP3(), hint, entry.getLength());
        }
    }
    
    public void pause(int ms) {
        MP3Player.getPlayer().addPause(ms);
    }

    public void addRegisterListener(RegisterListener listener) {
        listeners.add(listener);
    }
}

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

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Instance {
    
    private final Command command;
    private int label = -1;
    private int firstArgument = 0;
    private int secondArgument = 0;
    
    public Instance(Command command) {
        this.command = command;
    }
    
    public boolean execute(Emulator emulator) {
        Method method = command.getMethod();
        try {
            if(command.firstArgumentIsLabel()) {
                return((Boolean)method.invoke(null, emulator, firstArgument, secondArgument));
            } else {
                method.invoke(null, emulator, firstArgument, secondArgument);
                return(false);
            }
        } catch (IllegalAccessException ex) {
            throw new Error(ex);
        } catch (IllegalArgumentException ex) {
            throw new Error(ex);
        } catch (InvocationTargetException ex) {
            throw new Error(ex);
        }
    }
    
    public int getFirstArgument() {
        if(command.getNumberOfArguments() < 1) {
            throw new Error();
        }
        return(firstArgument);
    }
    
    public int getSecondArgument() {
        if(command.getNumberOfArguments() < 2) {
            throw new Error();
        }
        return(secondArgument);
    }
    
    private int removeRegister(String s) {
        if(s.startsWith("v") || s.startsWith("V")) {
            s = s.substring(1);
        }
        return(Integer.parseInt(s));
    }
    
    public void setFirstArgument(String argument) {
        if(command.getNumberOfArguments() < 1) {
            throw new Error();
        }
        firstArgument = removeRegister(argument);
    }
    
    public void setSecondArgument(String argument) {
        if(command.getNumberOfArguments() < 2) {
            throw new Error();
        }
        secondArgument = removeRegister(argument);
    }
    
    public Command getCommand() {
        return(command);
    }
    
    public void setLabel(int label) {
        this.label = label;
    }
    
    public int getLabel() {
        return(label);
    }

    public void compile(DataOutputStream out) throws IOException {
        out.writeShort(command.getCode());
        if(command.firstArgumentIsLabel()) {
            out.writeShort(label);
        } else {
            if(command.getNumberOfArguments() > 0) {
                out.writeShort(firstArgument);
            }
            if(command.getNumberOfArguments() > 1) {
                out.writeShort(secondArgument);
            }
        }
    }
    
}

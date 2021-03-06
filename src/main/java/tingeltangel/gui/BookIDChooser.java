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

package tingeltangel.gui;

import tingeltangel.tools.Callback;
import java.awt.Dialog;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import tingeltangel.core.Repository;
import tingeltangel.core.Translator;
import tingeltangel.core.Tupel;

/**
 *
 * @author mdames
 */
public class BookIDChooser extends javax.swing.JDialog {

    private final LinkedList<Tupel<Integer, String>> idList = new LinkedList<Tupel<Integer, String>>();
    private MyListModel model = new MyListModel();
    private Callback<Integer> callback;
    
    /**
     * Creates new form IDChooser
     */
    public BookIDChooser(java.awt.Frame parent, Callback<Integer> callback) {
        super(parent, false);
        initComponents();
        this.callback = callback;
        fillIDList();
        list.setModel(model);
        setVisible(true);
    }
    
    public BookIDChooser(Dialog parent, Callback<Integer> callback) {
        super(parent, false);
        initComponents();
        this.callback = callback;
        fillIDList();
        list.setModel(model);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        list = new javax.swing.JList();
        button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        list.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(list);

        button.setText("OK");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 376, Short.MAX_VALUE)
                        .addComponent(button)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fillIDList() {
        Integer[] ids = Repository.getIDs();
        for(int i = 0; i < ids.length; i++) {
            idList.add(new Tupel(ids[i], renderName(ids[i])));
        }
    }
    
    private String renderName(int id) {
        String name = Integer.toString(id);
        while(name.length() < 5) {
            name = "0" + name;
        }
        HashMap<String, String> txt = Repository.getBookTxt(id);
        if(txt != null) {
            name += " " + txt.get("Name") + " (" + txt.get("Author") + ")";
        }
        return(name);
    }
    
    private void buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonActionPerformed
        int index = list.getSelectedIndex();
        if(index != -1) {
            int id = idList.get(index).a;
            callback.callback(id);
            setVisible(false);
        }
    }//GEN-LAST:event_buttonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BookIDChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookIDChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookIDChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookIDChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                BookIDChooser dialog = new BookIDChooser(new javax.swing.JFrame(), new Callback<Integer>() {

                    @Override
                    public void callback(Integer i) {
                        System.out.println(i);
                    }
                });
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList list;
    // End of variables declaration//GEN-END:variables



    class MyListModel implements ListModel {

        private LinkedList<ListDataListener> listeners = new LinkedList<ListDataListener>();

        @Override
        public int getSize() {
            return(idList.size());
        }

        @Override
        public Object getElementAt(int index) {
            return(idList.get(index).b);
        }

        @Override
        public void addListDataListener(ListDataListener l) {
            listeners.add(l);
        }

        @Override
        public void removeListDataListener(ListDataListener l) {
            listeners.remove(l);
        }
        
        public void refresh() {
            Iterator<ListDataListener> i = listeners.iterator();
            while(i.hasNext()) {
                i.next().contentsChanged(null);
            }   
        }
    }
}

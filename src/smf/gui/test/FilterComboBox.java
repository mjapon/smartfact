/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.test;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class FilterComboBox extends JComboBox {
    private List<String> array;
    private int currentCaretPosition=0;


    public FilterComboBox(List<String> array) {
        super(array.toArray());
        this.array = array;
        this.setEditable(true);
        final JTextField textfield = (JTextField) this.getEditor().getEditorComponent();
        
        textfield.addKeyListener(new KeyAdapter() {           
            
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        currentCaretPosition=textfield.getCaretPosition();
                        if(textfield.getSelectedText()==null)
                        {
                        textfield.setCaretPosition(0);
                        comboFilter(textfield.getText());
                        textfield.setCaretPosition(currentCaretPosition);
                        }
                     }
                });
            }

        });

    }

    public void comboFilter(String enteredText) {
        List<String> filterArray= new ArrayList<String>();
        for (int i = 0; i < array.size(); i++) {
            //if (array.get(i).toLowerCase().contains(enteredText.toLowerCase())) {
            if (array.get(i).toLowerCase().startsWith(enteredText.toLowerCase())) {
                filterArray.add(array.get(i));
            }


        }
        if (filterArray.size() > 0) {

            this.setModel(new DefaultComboBoxModel(filterArray.toArray()));
            this.setSelectedItem(enteredText);
            this.showPopup();
        }
        else {
            this.hidePopup();
        }
    }

    /* Testing Codes */
    public static List<String> populateArray() {
        
                String[] initList = new String[]{"Guayaquil",
"Quito",
"Cuenca",
"Santo Domingo",
"Machala",
"Durán",
"Manta",
"Portoviejo",
"Loja",
"Ambato",
"Esmeraldas",
"Quevedo",
"Riobamba",
"Milagro",
"Ibarra",
"La Libertad",
"Babahoyo",
"Sangolquí",
"Daule",
"Latacunga",
"Tulcán",
"Chone",
"Pasaje",
"Santa Rosa",
"Nueva Loja",
"Huaquillas",
"El Carmen",
"Montecristi",
"Samborondón",
"Puerto Francisco de Orellana",
"Jipijapa",
"Santa Elena",
"Otavalo",
"Cayambe",
"Buena Fe",
"Ventanas",
"Velasco Ibarra",
"La Troncal",
"El Triunfo",
"Salinas",
"Playas",
"Azogues",
"Puyo",
"Vinces",
"La Concordia",
"Quinindé",
"Balzar",
"Naranjito",
"Naranjal",
"Guaranda",
"La Maná",
"Tena",
"San Lorenzo",
"Catamayo",
"El Guabo",
"Pedernales",
"Atuntaqui",
"Bahía de Caráquez",
"Pedro Carbo",
"Macas",
"Yaguachi",
"Calceta",
"Arenillas",
"Jaramijó",
"Valencia",
"Machachi",
"Shushufindi",
"Atacames",
"Piñas",
"San Gabriel",
"Gualaceo",
"Lomas de Sargentillo",
"Cañar",
"Cariamanga",
"Baños de Agua Santa",
"Montalvo",
"Macará",
"Salcedo",
"Zamora",
"Puerto Ayora",
"La Joya de los Sachas",
"Salitre",
"Tosagua",
"Pelileo",
"Pujilí",
"Tabacundo",
"Puerto López",
"San Vicente",
"Santa Ana",
"Zaruma",
"Balao",
"Rocafuerte",
"Yantzaza",
"Cotacachi",
"Santa Lucía",
"Cumandá",
"Palestina",
"Alfredo Baquerizo Moreno (Jujan)",
"Nobol",
"Mocache",
"Puebloviejo",
"Portovelo",
"Sucúa",
"Guano",
"Píllaro",
"Simón Bolívar",
"Gualaquiza",
"Paute",
"Saquisilí",
"Cnel. Marcelino Maridueña",
"Paján",
"San Miguel",
"Puerto Baquerizo Moreno",
"Catacocha",
"Palenque",
"Alausí",
"Caluma",
"Catarama",
"Flavio Alfaro",
"Colimes",
"Echeandía",
"Jama",
"Gral. Antonio Elizalde (Bucay)",
"Isidro Ayora",
"Muisne",
"Santa Isabel",
"Pedro Vicente Maldonado",
"Biblián",
"Archidona",
"Junín",
"Baba",
"Valdez (Limones)",
"Pimampiro",
"Camilo Ponce Enríquez",
"San Miguel de Los Bancos",
"El Tambo",
"Quinsaloma",
"El Ángel",
"Alamor",
"Chambo",
"Chimbo",
"Celica",
"Chordeleg",
"Balsas",
"Saraguro",
"El Chaco",
"Girón",
"Huaca",
"Pichincha",
"Chunchi",
"Pallatanga",
"Marcabelí",
"Sígsig",
"Gral. Leonidas Plaza Gutiérrez (Limón)",
"Urcuquí",
"Loreto",
"Rioverde",
"Zumba",
"Palora",
"Mira",
"El Pangui",
"Puerto Quito",
"Bolívar",
"Sucre",
"Chillanes",
"Quero",
"Guamote",
"Cevallos",
"Zapotillo",
"Villa La Unión (Cajabamba)",
"Santiago de Méndez",
"Zumbi",
"Puerto El Carmen de Putumayo",
"Patate",
"Olmedo",
"Puerto Villamil",
"El Dorado de Cascales",
"Lumbaqui",
"Palanda",
"Sigchos",
"Pindal",
"Guayzimi",
"Baeza",
"El Corazón",
"Paccha",
"Amaluza",
"Las Naves",
"Logroño",
"San Fernando",
"Gonzanamá",
"San Juan Bosco",
"Yacuambi",
"Santa Clara",
"Arajuno",
"Tarapoa",
"Tisaleo",
"Suscal",
"Nabón",
"Nuevo Rocafuerte",
"Mocha",
"La Victoria",
"Guachapala",
"Santiago",
"Chaguarpamba",
"Penipe",
"Taisha",
"Chilla",
"Paquisha",
"Carlos Julio Arosemena Tola",
"Sozoranga",
"Pucará",
"Huamboya",
"Quilanga",
"Oña",
"Sevilla de Oro",
"Mera",
"Pablo Sexto",
"Olmedo",
"Déleg",
"La Bonita",
"El Pan",
"Tiputini"};
                
        List<String> test = new ArrayList<String>();
        
        for (String dd: initList){
            test.add(dd);
        }
        
        /*
        test.add("");
        test.add("Mountain Flight");
        test.add("Mount Climbing");
        test.add("Trekking");
        test.add("Rafting");
        test.add("Jungle Safari");
        test.add("Bungie Jumping");
        test.add("Para Gliding");
        */
        
        return test;
    }

    public static void makeUI() {
        JFrame frame = new JFrame("Adventure in Nepal - Combo Filter Test");
        frame.setLayout(new BorderLayout());
        FilterComboBox acb = new FilterComboBox(populateArray());
        acb.setSize(300, 200);
        //frame.getContentPane().add(acb);
        frame.add(acb);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        makeUI();
    }
}
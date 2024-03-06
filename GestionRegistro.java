package yordi.digital;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class Persona {
    String nombre;
    int edad;

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }
}

class AyudaVivienda {
    List<Persona> beneficiarios;
    boolean recibida;

    public AyudaVivienda() {
        this.beneficiarios = new ArrayList<>();
        this.recibida = false;
    }

    // Método para registrar beneficiarios de la ayuda
    public void registrarBeneficiario(Persona persona) {
        if (!recibida && !beneficiarios.contains(persona) && persona.edad >= 18) {
            beneficiarios.add(persona);
        } else {
            System.out.println("No se puede registrar a la persona para esta ayuda.");
        }
    }

    // Método para marcar la ayuda como recibida
    public void recibirAyuda() {
        recibida = true;
    }
}

class AyudaDespensa {
    Persona beneficiario;
    boolean recibida;

    public AyudaDespensa() {
        this.recibida = false;
    }

    // Método para registrar beneficiario de la ayuda
    public void registrarBeneficiario(Persona persona) {
        if (!recibida && beneficiario == null && persona.edad >= 18) {
            beneficiario = persona;
        } else {
            System.out.println("No se puede registrar a la persona para esta ayuda.");
        }
    }

    // Método para marcar la ayuda como recibida
    public void recibirAyuda() {
        recibida = true;
    }
}

class AyudaCursos {
    Map<String, Persona> inscritos;
    boolean convocatoriaAbierta;

    public AyudaCursos() {
        this.inscritos = new HashMap<>();
        this.convocatoriaAbierta = false;
    }

    // Método para abrir la convocatoria
    public void abrirConvocatoria() {
        convocatoriaAbierta = true;
    }

    // Método para cerrar la convocatoria
    public void cerrarConvocatoria() {
        convocatoriaAbierta = false;
        inscritos.clear();
    }

    // Método para registrar inscrito en un curso
    public void registrarInscrito(String curso, Persona persona) {
        if (convocatoriaAbierta && !inscritos.containsKey(curso) && !inscritos.containsValue(persona) && persona.edad >= 18) {
            inscritos.put(curso, persona);
        } else {
            System.out.println("No se puede registrar a la persona para este curso.");
        }
    }
}

public class GestionRegistro {
    public static void main(String[] args) {
        // Ejemplo de uso

        try {
            // Crear un libro de trabajo de Excel
            XSSFWorkbook workbook = new XSSFWorkbook();

            // Crear hojas de Excel para cada tipo de ayuda
            XSSFSheet sheetVivienda = workbook.createSheet("AyudaVivienda");
            XSSFSheet sheetDespensa = workbook.createSheet("AyudaDespensa");
            XSSFSheet sheetCursos = workbook.createSheet("AyudaCursos");

            // Ayuda Vivienda
            AyudaVivienda ayudaVivienda = new AyudaVivienda();
            Persona persona1 = new Persona("Juan", 25);
            Persona persona2 = new Persona("María", 30);

            ayudaVivienda.registrarBeneficiario(persona1);
            ayudaVivienda.registrarBeneficiario(persona2);
            ayudaVivienda.recibirAyuda();

            // Guardar datos de Ayuda Vivienda en la hoja de Excel
            guardarDatos(sheetVivienda, ayudaVivienda);

            // Ayuda Despensa
            AyudaVivienda despensa = new AyudaVivienda();
            despensa.registrarBeneficiario(persona1);
            despensa.recibirAyuda();

            // Guardar datos de Ayuda Despensa en la hoja de Excel
            guardarDatos(sheetDespensa, despensa );

            // Ayuda Cursos
            AyudaVivienda ayudaCursos = new AyudaVivienda();
            ayudaCursos.abrirConvocatoria();
            ayudaCursos.registrarInscrito("Corte y Confección", persona1);
            ayudaCursos.registrarInscrito("Decoración con Globos", persona2);
            ayudaCursos.cerrarConvocatoria();

            // Guardar datos de Ayuda Cursos en la hoja de Excel
            guardarDatos(sheetCursos, ayudaCursos);

            // Guardar el libro de trabajo de Excel en un archivo
            FileOutputStream outputStream = new FileOutputStream("Ayudas.xlsx");
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

            System.out.println("Datos guardados en el archivo Ayudas.xlsx");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para guardar datos en la hoja de Excel
    private static void guardarDatos(XSSFSheet sheet, AyudaVivienda ayuda) {
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("Beneficiarios");

        int rowNum = 1;
        for (Persona persona : ayuda.beneficiarios) {
            Row dataRow = sheet.createRow(rowNum++);
            Cell dataCell = dataRow.createCell(0);
            dataCell.setCellValue(persona.nombre);
        }

        Row recibidaRow = sheet.createRow(rowNum++);
        Cell recibidaCell = recibidaRow.createCell(0);
        recibidaCell.setCellValue("Recibida");
        Row recibidaValueRow = sheet.createRow(rowNum);
        Cell recibidaValueCell = recibidaValueRow.createCell(0);
        recibidaValueCell.setCellValue(ayuda.recibida ? "Sí" : "No");
    }

    
}
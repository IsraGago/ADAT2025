package ud1.refuerzo.biblioteca.persistencia;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ud1.refuerzo.biblioteca.clases.Copia;
import ud1.refuerzo.biblioteca.clases.Libro;
import ud1.refuerzo.biblioteca.excepciones.ExcepcionXML;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BibliotecaXML {
    Document documentoXML;

    public Document cargarDocumentoXML(String rutaXML, TipoValidacion validacion) throws ExcepcionXML {
        try {
            documentoXML = XMLDOMUtils.cargarDocumentoXML(rutaXML, validacion);
        } catch (Exception e) {
            throw new ExcepcionXML("Error al cargar el documento XML: " + e.getMessage());
        }
        return documentoXML;
    }

    public LinkedHashMap<String, Integer> getNumLibrosPorSeccion(ArrayList<String> secciones) {
        Map<String, Integer> mapaSecciones = new HashMap<>();
        for (String seccion : secciones) {
            double numLibrosSeccion = XMLDOMUtils.evaluarXPathNumber(documentoXML, "count(//seccion[@nombre =\"" + seccion + "\"]//libro)");
            mapaSecciones.put(seccion, (int) numLibrosSeccion);
        }

        LinkedHashMap<String, Integer> mapaOrdenado = new LinkedHashMap<>();
        mapaSecciones.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(entry -> mapaOrdenado.put(entry.getKey(), entry.getValue()));

        return mapaOrdenado;
    }


    public ArrayList<Libro> getLibrosPorSeccion(String seccion) {
        NodeList nodosLibros = XMLDOMUtils.evaluarXPathNodeList(documentoXML, "//seccion[@nombre =\"" + seccion + "\"]//libro");
        if (nodosLibros.getLength() == 0) {
            return null;
        }
        ArrayList<Libro> libros = new ArrayList<>();

        for (int i = 0; i < nodosLibros.getLength(); i++) {
            if (nodosLibros.item(i) instanceof Element libroElem) {
                Libro libro = crearLibro(libroElem);
                if (libro != null) {
                    libros.add(libro);
                }
            }
        }

        return libros;
    }

    private Libro crearLibro(Element libroElem) {
        try {
            Libro libro = new Libro();
            libro.setID(libroElem.getAttribute("ID"));
            libro.setIsbn(libroElem.getAttribute("isbn"));
            libro.setTitulo(libroElem.getAttribute("titulo"));
            libro.setNumero_de_paginas(Integer.parseInt(libroElem.getAttribute("numero_de_paginas")));
            LocalDate fechaEdicion = LocalDate.parse(XMLDOMUtils.obtenerTexto(libroElem, "fechaEdicion"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            libro.setFechaEdicion(fechaEdicion);
            libro.setEditorial(XMLDOMUtils.obtenerTexto(libroElem, "editorial"));
            libro.setPrecio(Double.parseDouble(XMLDOMUtils.obtenerTexto(libroElem, "precio")));
            libro.setAutores(cargarAutores(libroElem));
            libro.setCopias(cargarCopias(libroElem));
            return libro;
        } catch (Exception e) {
            throw new ExcepcionXML("Error al crear el libro desde el elemento XML: " + e.getMessage());
        }
    }


    private List<Copia> cargarCopias(Element libroElem) {
        ArrayList<Copia> listaCopias = new ArrayList<>();
        Element copiasElem = (Element) libroElem.getElementsByTagName("copias").item(0);
        if (copiasElem != null) {
            NodeList copias = copiasElem.getElementsByTagName("copia");
            for (int i = 0; i < copias.getLength(); i++) {
                if (copias.item(i) instanceof Element copiaElem) {
                    Copia copia = new Copia();
                    copia.setNumero(Integer.parseInt(copiaElem.getAttribute("numero")));
                    copia.setEstado(copiaElem.getAttribute("estado"));
                    listaCopias.add(copia);
                }
            }
        }

        return listaCopias;
    }

    private List<String> cargarAutores(Element libroElem) {
        ArrayList<String> autores = new ArrayList<>();

        Element autoresElem = (Element) libroElem.getElementsByTagName("autores").item(0);
        if (autoresElem != null) {
            NodeList autoresNodos = autoresElem.getElementsByTagName("autor");
            for (int i = 0; i < autoresNodos.getLength(); i++) {
                if (autoresNodos.item(i) instanceof Element autorElem) {
                    String autor = autorElem.getTextContent();
                    autores.add(autor);
                }
            }
        }

        return autores;
    }

    public ArrayList<String> getSecciones() {
        ArrayList<String> secciones = new ArrayList<>();
        NodeList nodosSecciones = XMLDOMUtils.evaluarXPathNodeList(documentoXML, "//seccion/@nombre");
        for (int i = 0; i < nodosSecciones.getLength(); i++) {
            if (nodosSecciones.item(i) instanceof Attr atributoNombre) {
                String nombreSeccion = atributoNombre.getTextContent();
                secciones.add(nombreSeccion);
            }
        }
        return secciones;
    }

    public void modificarEstadoCopia(String isbn, int numCopia, String nuevoEstado) {
        NodeList nodosLibros = XMLDOMUtils.evaluarXPathNodeList(documentoXML, "//libro[@isbn=\"" + isbn + "\"]");
        if (nodosLibros.getLength() == 0) {
            throw new ExcepcionXML("No se encontró ningún libro con el ISBN: " + isbn);
        }
        Element libroElem = (Element) nodosLibros.item(0);
        Element copiasElem = (Element) libroElem.getElementsByTagName("copias").item(0);
        if (copiasElem != null) {
            NodeList copias = copiasElem.getElementsByTagName("copia");
            for (int i = 0; i < copias.getLength(); i++) {
                if (copias.item(i) instanceof Element copiaElem) {
                    int numeroCopia = Integer.parseInt(copiaElem.getAttribute("numero"));
                    if (numeroCopia == numCopia) {
                        copiaElem.setAttribute("estado", nuevoEstado);
                        return;
                    }
                }
            }
        }
        throw new ExcepcionXML("No se encontró ninguna copia con el número: " + numCopia + " para el libro con ISBN: " + isbn);
    }


    public void addLibro(Libro libro, String seccion) {
        // SI NO EXISTE SU SECCION LA CREA.
        NodeList nodosSeccion = XMLDOMUtils.evaluarXPathNodeList(documentoXML, "//seccion[@nombre =\"" + seccion + "\"]");

        Element seccionElem;
        Element librosElem;

        if (nodosSeccion.getLength() > 0) {
            seccionElem = (Element) nodosSeccion.item(0);
            librosElem = (Element) seccionElem.getElementsByTagName("libros").item(0);
            if (librosElem == null) {
                librosElem = documentoXML.createElement("libros");
                seccionElem.appendChild(librosElem);
            }
        } else {
            //LA SECCION NO EXISTE
            Element seccionesElem = (Element) documentoXML.getElementsByTagName("secciones").item(0);
            seccionElem = documentoXML.createElement("seccion");
            seccionElem.setAttribute("nombre", seccion);

            librosElem = documentoXML.createElement("libros");
            seccionElem.appendChild(librosElem);
            seccionesElem.appendChild(seccionElem);
        }

        Element libroElem = crearLibroElement(libro);
        librosElem.appendChild(libroElem);
    }

    private Element crearLibroElement(Libro libro) {
        Element libroElem = documentoXML.createElement("libro");
        Element autoresElem = documentoXML.createElement("autores");
        Element copiasElem = documentoXML.createElement("copias");

        libroElem.appendChild(autoresElem);
        libroElem.appendChild(copiasElem);

        // ATRIBUTOS
        XMLDOMUtils.addAtributoId(documentoXML, "ID", libro.getID(), libroElem);
        XMLDOMUtils.addAtributo(documentoXML, "isbn", libro.getIsbn(), libroElem);
        XMLDOMUtils.addAtributo(documentoXML, "titulo", libro.getTitulo(), libroElem);
        XMLDOMUtils.addAtributo(documentoXML, "numero_de_paginas", String.valueOf(libro.getNumero_de_paginas()), libroElem);

        // ELEMENTOS
        XMLDOMUtils.addElement(documentoXML, "fechaEdicion", libro.getFechaEdicion().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), libroElem);
        XMLDOMUtils.addElement(documentoXML, "editorial", libro.getEditorial(), libroElem);
        XMLDOMUtils.addElement(documentoXML, "precio", String.valueOf(libro.getPrecio()), libroElem);

        for (String autor : libro.getAutores()) {
            Element autorELem = XMLDOMUtils.addElement(documentoXML, "autor", autor, autoresElem);
            autoresElem.appendChild(autorELem);
        }
        for (Copia copia : libro.getCopias()) {
            Element copiaElem = documentoXML.createElement("copia");
            XMLDOMUtils.addAtributo(documentoXML, "numero", String.valueOf(copia.getNumero()), copiaElem);
            XMLDOMUtils.addAtributo(documentoXML, "estado", copia.getEstado(), copiaElem);
            copiasElem.appendChild(copiaElem);
        }

        return libroElem;
    }

}

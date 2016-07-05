package com.example.matheus.casadocodigocomlibs.event;

import com.example.matheus.casadocodigocomlibs.model.Autor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matheus on 05/07/16.
 */
public class AutorEvent {
    public final ArrayList<Autor> autores;

    public AutorEvent(ArrayList<Autor> autores) {

        this.autores = autores;
    }
}

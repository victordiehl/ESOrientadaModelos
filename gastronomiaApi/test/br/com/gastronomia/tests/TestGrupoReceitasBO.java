package br.com.gastronomia.tests;

import br.com.gastronomia.bo.GrupoReceitasBO;
import br.com.gastronomia.dao.GrupoReceitasDAO;
import br.com.gastronomia.exception.ValidationException;
import br.com.gastronomia.model.GrupoReceitas;
import br.com.gastronomia.model.Ingrediente;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TestGrupoReceitasBO {

    GrupoReceitasBO grupoReceitasBO;

    @Mock
    GrupoReceitasDAO grupoReceitasDAO;

    @Before
    public void init(){
        grupoReceitasBO = new GrupoReceitasBO();
        grupoReceitasBO.setGrupoReceitasDAO(grupoReceitasDAO);
    }

    @Test
    public void testCreateGrupoReceita() throws NoSuchAlgorithmException,  ValidationException {
        GrupoReceitas grupoReceitas = new GrupoReceitas();
        Mockito.when(grupoReceitasDAO.save(grupoReceitas)).thenReturn((long) 0);
        boolean retorno = grupoReceitasBO.createGroup(grupoReceitas);
        assertTrue(retorno);
    }

//    @Test
//    public void testListGroups() {
//        ArrayList<GrupoReceitas> grupoReceitasArrayList = new ArrayList<>();
//        GrupoReceitas grupoReceitas = new GrupoReceitas();
//        grupoReceitas.setNome("Chambinho");
//        grupoReceitasArrayList.add(grupoReceitas);
//        Mockito.when(grupoReceitasDAO.listForName(grupoReceitas, "Chambinho")).thenReturn(grupoReceitasArrayList);
//        HashMap<String, List<GrupoReceitas>> listGrupoReceitas = grupoReceitasBO.listGroups();
//        assertEquals(grupoReceitasArrayList.get(0), listGrupoReceitas.get("GrupoReceitas").get(0));
//    }
//
//    @Test
//    public void testListAllGroup() {
//        ArrayList<GrupoReceitas> grupoReceitasArrayList = new ArrayList<>();
//        grupoReceitasArrayList.add(new GrupoReceitas());
//        Mockito.when(grupoReceitasDAO.listAll(GrupoReceitas.class)).thenReturn(grupoReceitasArrayList);
//        HashMap<String, List<GrupoReceitas>> listGrupoReceitas = new HashMap<String, List<GrupoReceitas>>();
//        listGrupoReceitas.put("1",grupoReceitasArrayList);
//        listGrupoReceitas = grupoReceitasBO.listGroups();
//        assertEquals(grupoReceitasArrayList.get(0), listGrupoReceitas.get("1").get(0));
//    }

    @Test
    public void testUpdateGroup() throws ValidationException{
        GrupoReceitas grupoReceitas = new GrupoReceitas();
        Mockito.when(grupoReceitasDAO.updateGroup(grupoReceitas)).thenReturn((long)1);
        long retorno = grupoReceitasBO.updateGroup(grupoReceitas);
        assertEquals(1,retorno);
    }

    @Test
    public void testActiveGroup() {
        Mockito.when(grupoReceitasDAO.alterStatus(0, true)).thenReturn((long)0);
        long result = grupoReceitasBO.activateGroup(0);
        assertEquals(result, 0);
    }

    @Test
    public void testGroupDeactiveGroup() {
        Mockito.when(grupoReceitasDAO.alterStatus(0, true)).thenReturn((long)0);
        long result = grupoReceitasBO.deactivateGroup(0);
        assertEquals(result, 0);
    }

    @Test
    public void testGetGroupByCod() throws ValidationException {
        GrupoReceitas grupoReceitas = new GrupoReceitas();
        Mockito.when(grupoReceitasDAO.findGroupByID(1)).thenReturn(grupoReceitas);
        GrupoReceitas grupoReceitas2 = grupoReceitasBO.getGroupByCod(1);
        assertEquals(grupoReceitas, grupoReceitas2);
    }

//    @Test
//    public void testGetGroupByCodFalse() throws ValidationException {
//        GrupoReceitas grupoReceitas = new GrupoReceitas();
//        Mockito.when(grupoReceitasDAO.findGroupByID(1)).thenReturn(grupoReceitas);
//        boolean resultado;
//        try {
//            GrupoReceitas grupoReceitas2 = grupoReceitasBO.getGroupByCod(0);
//
//            assert
//        }   catch (ValidationException e) {
//            resultado = false;
//            }
//        assertTrue(resultado);
//
//    }
}

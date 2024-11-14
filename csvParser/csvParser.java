import java.io.*;
import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class csvParser {
    public static void main(String[] args) {
        String basePath = System.getProperty("user.dir") + File.separator + "social_network-csv_basic-sf0.1";
        String resultadosFolder = System.getProperty("user.dir") + File.separator + "resultados" + File.separator;
        String resultadosMain = System.getProperty("user.dir");
        Map<String, String> nodeFiles = new HashMap<>();
        nodeFiles.put("person", basePath + File.separator + "dynamic" + File.separator + "person_0_0.csv");
        nodeFiles.put("comment", basePath + File.separator + "dynamic" + File.separator + "comment_0_0.csv");
        nodeFiles.put("place", basePath + File.separator + "static" + File.separator + "place_0_0.csv");
        nodeFiles.put("organisation", basePath + File.separator + "static" + File.separator + "organisation_0_0.csv");
        nodeFiles.put("forum", basePath + File.separator + "dynamic" + File.separator + "forum_0_0.csv");
        nodeFiles.put("post", basePath + File.separator + "dynamic" + File.separator + "post_0_0.csv");
        nodeFiles.put("tag", basePath + File.separator + "static" + File.separator + "tag_0_0.csv");
        nodeFiles.put("tagClass", basePath + File.separator + "static" + File.separator + "tagclass_0_0.csv");


        Map<String, String> relationFiles = new HashMap<>();
        relationFiles.put("commentCreator", basePath + File.separator + "dynamic" + File.separator + "comment_hasCreator_person_0_0.csv");
        relationFiles.put("personLikes", basePath + File.separator + "dynamic" + File.separator + "person_likes_comment_0_0.csv");
        relationFiles.put("commentReplyOfComment", basePath + File.separator + "dynamic" + File.separator + "comment_replyOf_comment_0_0.csv");
        relationFiles.put("postCreator", basePath + File.separator + "dynamic" + File.separator + "post_hasCreator_person_0_0.csv");
        relationFiles.put("forumPost", basePath + File.separator + "dynamic" + File.separator + "forum_containerOf_post_0_0.csv");
        relationFiles.put("forumMember", basePath + File.separator + "dynamic" + File.separator + "forum_hasMember_person_0_0.csv");
        relationFiles.put("forumModerator", basePath + File.separator + "dynamic" + File.separator + "forum_hasModerator_person_0_0.csv");
        relationFiles.put("personLikesPost", basePath + File.separator + "dynamic" + File.separator + "person_likes_post_0_0.csv");
        relationFiles.put("personHasInterest", basePath + File.separator + "dynamic" + File.separator + "person_hasInterest_tag_0_0.csv");
        relationFiles.put("forumHasTag", basePath + File.separator + "dynamic" + File.separator + "forum_hasTag_tag_0_0.csv");
        relationFiles.put("postHasTag", basePath + File.separator + "dynamic" + File.separator + "post_hasTag_tag_0_0.csv");
        relationFiles.put("commentHasTag", basePath + File.separator + "dynamic" + File.separator + "comment_hasTag_tag_0_0.csv");
        relationFiles.put("tagIsSubClassOf", basePath + File.separator + "static" + File.separator + "tagclass_isSubclassOf_tagclass_0_0.csv");
        relationFiles.put("commentIsLocatedIn", basePath + File.separator + "dynamic" + File.separator + "comment_isLocatedIn_place_0_0.csv");
        relationFiles.put("personIsLocatedIn", basePath + File.separator + "dynamic" + File.separator + "person_isLocatedIn_place_0_0.csv");
        relationFiles.put("personStudyAt", basePath + File.separator + "dynamic" + File.separator + "person_studyAt_organisation_0_0.csv");
        relationFiles.put("personWorkAt", basePath + File.separator + "dynamic" + File.separator + "person_workAt_organisation_0_0.csv");
        relationFiles.put("placeIsPartOf", basePath + File.separator + "static" + File.separator + "place_isPartOf_place_0_0.csv");
        relationFiles.put("tagHasType", basePath + File.separator + "static" + File.separator + "tag_hasType_tagclass_0_0.csv");
        relationFiles.put("commentReplyOfPost", basePath + File.separator + "dynamic" + File.separator + "comment_replyOf_post_0_0.csv");
        relationFiles.put("organisationIsLocatedIn", basePath + File.separator + "static" + File.separator + "organisation_isLocatedIn_place_0_0.csv");
        relationFiles.put("knows", basePath + File.separator + "dynamic" + File.separator + "person_knows_person_0_0.csv");
        relationFiles.put("postIsLocatedIn", basePath + File.separator + "dynamic" + File.separator + "post_isLocatedIn_place_0_0.csv");

        Map<String, String> titulosEdges = new HashMap<>();
        titulosEdges.put("commentCreator", "hasCreator");
        titulosEdges.put("personLikes", "likes");
        titulosEdges.put("commentReplyOfComment", "replyOf");
        titulosEdges.put("postCreator", "hasCreator");
        titulosEdges.put("forumPost", "containerOf");
        titulosEdges.put("forumMember", "hasMember");
        titulosEdges.put("forumModerator", "hasModerator");
        titulosEdges.put("personLikesPost", "likes");
        titulosEdges.put("personHasInterest", "hasInterest");
        titulosEdges.put("forumHasTag", "hasTag");
        titulosEdges.put("postHasTag", "hasTag");
        titulosEdges.put("commentHasTag", "hasTag");
        titulosEdges.put("tagIsSubClassOf", "isSubclassOf");
        titulosEdges.put("commentIsLocatedIn", "isLocatedIn");
        titulosEdges.put("personIsLocatedIn", "isLocatedIn");
        titulosEdges.put("personStudyAt", "studyAt");
        titulosEdges.put("personWorkAt", "workAt");
        titulosEdges.put("placeIsPartOf", "isPartOf");
        titulosEdges.put("tagHasType", "hasType");
        titulosEdges.put("commentReplyOfPost", "replyOf");
        titulosEdges.put("organisationIsLocatedIn", "isLocatedIn");
        titulosEdges.put("knows", "knows");
        titulosEdges.put("postIsLocatedIn", "isLocatedIn");
        
        

        Map<String, String> idMapping = new HashMap<>();
        List<String> lineasNodes = new ArrayList<>();
        List<String> lineasEdges = new ArrayList<>();
        //AtomicInteger nNodos = new AtomicInteger(0);
        //AtomicInteger nEdges = new AtomicInteger(0);
        System.out.println();

        nodeFiles.forEach((key, filePath) -> {
            try {
                File file = new File(filePath);
                Scanner scanner = new Scanner(file);
                int nodeCount = 0;
                List<String> nodeList = new ArrayList<>();
                while (scanner.hasNextLine()) {
                    String[] fila = scanner.nextLine().split("\\|");
                    if (fila.length > 0 && !fila[0].equals("id")) {
                        nodeCount++;
                        String originalId = fila[0];
                        
                        String newId = idMapping.get(originalId);
                        if (newId == null) {
                            newId = "N" + (idMapping.size() + 1);
                            idMapping.put(originalId, newId);
                        }
                
                        lineasNodes.add(newId + "," + newId);
                        nodeList.add(newId);
                    }
                }
                System.out.println("NODOS (" + key + "): " + nodeCount);
                scanner.close();

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultadosFolder + key + ".txt"))) {
                    for (String nodeId : nodeList) {
                        writer.write(nodeId);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    System.err.println("Error al escribir el archivo de salida: " + e.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        //System.out.println(nNodos);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Numero de nodos:");
        System.out.println(lineasNodes.size());
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println();
        // Procesar relaciones
        relationFiles.forEach((key, filePath) -> {
            List<String> edgeList = new ArrayList<>();
            try {
                File file = new File(filePath);
                Scanner scanner = new Scanner(file);
                int edgeCount = 0;
                while (scanner.hasNextLine()) {
                    String[] fila = scanner.nextLine().split("\\|");
                    if (fila.length > 1 && !fila[0].contains("id")) {
                        String sourceId = idMapping.get(fila[0]);
                        String targetId = idMapping.get(fila[1]);
                        if (sourceId != null && targetId != null) {
                            String keyAux=titulosEdges.get(key);
                            String edge = sourceId + "," + keyAux + "," + targetId;
                            edgeList.add(edge);
                            lineasEdges.add(edge);
                            edgeCount++;
                            //nEdges.incrementAndGet();
                        }
                    }
                }
                System.out.println("EDGES (" + key + "): " + edgeCount);
                scanner.close();

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultadosFolder + key + ".txt"))) {
                    for (String edge : edgeList) {
                        writer.write(edge);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    System.err.println("Error al escribir el archivo de salida de relación: " + e.getMessage());
                }   
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Numero de aristas:");
        System.out.println(lineasEdges.size());
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println();

        try (BufferedWriter nodeWriter = new BufferedWriter(new FileWriter(resultadosMain  + File.separator + "nodes.txt"));
             BufferedWriter edgeWriter = new BufferedWriter(new FileWriter(resultadosMain  + File.separator + "edges.txt"))) {

            for (String nodeLine : lineasNodes) {
                nodeWriter.write(nodeLine);
                nodeWriter.newLine();
            }
            System.out.println("Archivo final de nodos creado: "+resultadosMain + File.separator + "nodes.txt");

            for (String nodeLine : lineasEdges) {
                edgeWriter.write(nodeLine);
                edgeWriter.newLine();
            }
            System.out.println("Archivo final de nodos creado: "+resultadosMain + File.separator + "edges.txt");

        } catch (IOException e) {
            System.err.println("Error al escribir el archivo final de nodos: " + e.getMessage());
        }
    }
}

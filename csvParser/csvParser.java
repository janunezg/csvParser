import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class csvParser {

    public static void main(String[] args) {
        String basePath = System.getProperty("user.dir") + File.separator + "social_network-csv_basic-sf0.1";

        String person = basePath + File.separator + "dynamic" + File.separator + "person_0_0.csv";
        String comment = basePath + File.separator + "dynamic" + File.separator + "comment_0_0.csv";
        String knows = basePath + File.separator + "dynamic" + File.separator + "person_knows_person_0_0.csv";
        String commentCreator = basePath + File.separator + "dynamic" + File.separator + "comment_hasCreator_person_0_0.csv";
        String personLikes = basePath + File.separator + "dynamic" + File.separator + "person_likes_comment_0_0.csv";
        String commentReplyOf = basePath + File.separator + "dynamic" + File.separator + "comment_replyOf_comment_0_0.csv";
        String post = basePath + File.separator + "dynamic" + File.separator + "post_0_0.csv";
        String postCreator = basePath + File.separator + "dynamic" + File.separator + "post_hasCreator_person_0_0.csv";
        String forum = basePath + File.separator + "dynamic" + File.separator + "forum_0_0.csv";
        String forumPost = basePath + File.separator + "dynamic" + File.separator + "forum_containerOf_post_0_0.csv";
        String forumMember = basePath + File.separator + "dynamic" + File.separator + "forum_hasMember_person_0_0.csv";
        String forumModerator = basePath + File.separator + "dynamic" + File.separator + "forum_hasModerator_person_0_0.csv";
        String personLikesPost = basePath + File.separator + "dynamic" + File.separator + "person_likes_post_0_0.csv";
        String tag = basePath + File.separator + "static" + File.separator + "tag_0_0.csv";
        String tagClass = basePath + File.separator + "static" + File.separator + "tagclass_0_0.csv";
        String personHasInterest = basePath + File.separator + "dynamic" + File.separator + "person_hasInterest_tag_0_0.csv";
        String forumHasTag = basePath + File.separator + "dynamic" + File.separator + "forum_hasTag_tag_0_0.csv";
        String postHasTag = basePath + File.separator + "dynamic" + File.separator + "post_hasTag_tag_0_0.csv";
        String commentHasTag = basePath + File.separator + "dynamic" + File.separator + "comment_hasTag_tag_0_0.csv";
        String tagIsSubClassOf = basePath + File.separator + "static" + File.separator + "tagclass_isSubclassOf_tagclass_0_0.csv";

        
        String outputFilePath = System.getProperty("user.dir") + File.separator + "forum.txt";
        
        List<String> lineasNodes = new ArrayList<>();
        List<String> lineasEdges = new ArrayList<>();
        Map<String, String> idMapping = new HashMap<>();
        
        String nuevoCSV = System.getProperty("user.dir") + File.separator + "nodes.txt";
        String edges = System.getProperty("user.dir") + File.separator + "edges.txt"; 

        // Process nodes tag   
        try {
            File file = new File(tag);
            Scanner scanner = new Scanner(file);
            int tagCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 0 && !fila[0].equals("id")) {
                    tagCount++;
                    String originalId = fila[0];
                    String newId = "N" + tagCount;
                    idMapping.put(originalId, newId);
                    lineasNodes.add(newId + "," + newId);
                }
            }
            System.out.println("NODOS (tag): " + tagCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Process tagClass 
        try {
            File file = new File(tagClass);
            Scanner scanner = new Scanner(file);
            int tagCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 0 && !fila[0].equals("id")) {
                    tagCount++;
                    String originalId = fila[0];
                    String newId = "N" + tagCount;
                    idMapping.put(originalId, newId);
                    lineasNodes.add(newId + "," + newId);
                }
            }
            System.out.println("NODOS (tagClass): " + tagCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Process person
        try {
            File file = new File(person);
            Scanner scanner = new Scanner(file);
            int nodeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 0 && !fila[0].equals("id")) {
                    nodeCount++;
                    String originalId = fila[0];
                    String newId = "N" + nodeCount;
                    idMapping.put(originalId, newId);
                    lineasNodes.add(newId + "," + newId);
                }
            }
            System.out.println("NODOS (person): " + nodeCount);
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process knows file
        try {
            File file = new File(knows);
            Scanner scanner = new Scanner(file);
            int edgeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 2 && !fila[0].equals("Person.id")) {
                    String originalId1 = fila[0];
                    String originalId2 = fila[1];
                    String newId1 = idMapping.get(originalId1);
                    String newId2 = idMapping.get(originalId2);
                    if (newId1 != null && newId2 != null) {
                        String edge = newId1 + ",knows," + newId2;
                        lineasEdges.add(edge);
                        edgeCount++;
                    }
                }
            }
            System.out.println("EDGES (knows): " + edgeCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process comment
        try {
            File file = new File(comment);
            Scanner scanner = new Scanner(file);
            int commentCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 0 && !fila[0].equals("id")) {
                    commentCount++;
                    String originalId = fila[0];
                    String newId = "N" + (idMapping.size() + commentCount);
                    idMapping.put(originalId, newId);
                    lineasNodes.add(newId + "," + newId);
                }
            }
            System.out.println("NODOS (comment): " + commentCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process post
        try {
            File file = new File(post);
            Scanner scanner = new Scanner(file);
            int postCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 0 && !fila[0].equals("id")) {
                    postCount++;
                    String originalId = fila[0];
                    String newId = "N" + (idMapping.size() + postCount);
                    idMapping.put(originalId, newId);
                    lineasNodes.add(newId + "," + newId);
                }
            }
            System.out.println("NODOS (post): " + postCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Processforum
        try {
            File file = new File(forum);
            Scanner scanner = new Scanner(file);
            int forumCount = 0;
            List<String> forumm = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 0 && !fila[0].equals("id")) {
                    forumCount++;
                    String originalId = fila[0];
                    String newId = "N" + (idMapping.size() + forumCount);
                    idMapping.put(originalId, newId);
                    forumm.add(newId);
                    lineasNodes.add(newId + "," + newId);
                }
            }
            System.out.println("NODOS (forum): " + forumCount);
            scanner.close();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
                for (String forumItem : forumm) {
                    writer.write(forumItem);
                    writer.newLine();
                }
                System.out.println("Archivo creado con el contenido de forumm en: " + outputFilePath);
            } catch (IOException e) {
                System.err.println("Error al escribir el archivo de salida: " + e.getMessage());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // organisation
        try {
            File file = new File(basePath + File.separator + "static" + File.separator + "organisation_0_0.csv");
            Scanner scanner = new Scanner(file);
            int orgCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 0 && !fila[0].equals("id")) {
                    orgCount++;
                    String originalId = fila[0];
                    String newId = "N" + (idMapping.size() + orgCount);
                    idMapping.put(originalId, newId);
                    lineasNodes.add(newId + "," + newId);
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // place
        try {
            File file = new File(basePath + File.separator + "static" + File.separator + "place_0_0.csv");
            Scanner scanner = new Scanner(file);
            int placeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 0 && !fila[0].equals("id")) {
                    placeCount++;
                    String originalId = fila[0];
                    String newId = "N" + (idMapping.size() + placeCount);
                    idMapping.put(originalId, newId);
                    lineasNodes.add(newId + "," + newId);
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //--------------------------------------------------------------------------------------------------
        //EDGES:
//-----------------------------------------------------------------------------------------------------

        // Process nodes file (forum members)
        try {
            File file = new File(forumMember);
            Scanner scanner = new Scanner(file);
            int memberCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("Forum.id")) {
                    memberCount++;
                    System.out.println(fila[0]+"  "+fila[1]);
                    String originalForumId = fila[0];
                    String originalPersonId = fila[1];
                    String newForumId = idMapping.get(originalForumId);
                    String newPersonId = idMapping.get(originalPersonId);
                    if (newForumId != null && newPersonId != null) {
                        String edge = newForumId + ",hasMember," + newPersonId;
                        lineasEdges.add(edge);
                    }
                }
            }
            System.out.println("EDGES (forum_hasMember_person): " + memberCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process nodes file (forum moderators)
        try {
            File file = new File(forumModerator);
            Scanner scanner = new Scanner(file);
            int moderatorCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("Forum.id")) {
                    moderatorCount++;
                    String originalForumId = fila[0];
                    String originalPersonId = fila[1];
                    String newForumId = idMapping.get(originalForumId);
                    String newPersonId = idMapping.get(originalPersonId);
                    if (newForumId != null && newPersonId != null) {
                        String edge = newForumId + ",hasModerator," + newPersonId;
                        lineasEdges.add(edge);
                    }
                }
            }
            System.out.println("EDGES (forum_hasModerator_person): " + moderatorCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Process comment_hasCreator_person file
        try {
            File file = new File(commentCreator);
            Scanner scanner = new Scanner(file);
            int edgeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("Comment.id")) {
                    String originalCommentId = fila[0];
                    String originalPersonId = fila[1];
                    String newCommentId = idMapping.get(originalCommentId);
                    String newPersonId = idMapping.get(originalPersonId);
                    if (newCommentId != null && newPersonId != null) {
                        String edge = newCommentId + ",hasCreator," + newPersonId;
                        lineasEdges.add(edge);
                        edgeCount++;
                    }
                }
            }
            System.out.println("EDGES (comment_hasCreator): " + edgeCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process person_likes_comment file
        try {
            File file = new File(personLikes);
            Scanner scanner = new Scanner(file);
            int edgeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 2 && !fila[0].equals("Person.id")) {
                    String originalPersonId = fila[0];
                    String originalCommentId = fila[1];
                    String newPersonId = idMapping.get(originalPersonId);
                    String newCommentId = idMapping.get(originalCommentId);
                    if (newPersonId != null && newCommentId != null) {
                        String edge = newPersonId + ",likes," + newCommentId;
                        lineasEdges.add(edge);
                        edgeCount++;
                    }
                }
            }
            System.out.println("EDGES (person_likes_comment): " + edgeCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process comment_replyOf_comment file
        try {
            File file = new File(commentReplyOf);
            Scanner scanner = new Scanner(file);
            int edgeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("Comment.id")) {
                    String originalCommentId1 = fila[0];
                    String originalCommentId2 = fila[1];
                    String newCommentId1 = idMapping.get(originalCommentId1);
                    String newCommentId2 = idMapping.get(originalCommentId2);
                    if (newCommentId1 != null && newCommentId2 != null) {
                        String edge = newCommentId1 + ",replyOf," + newCommentId2;
                        lineasEdges.add(edge);
                        edgeCount++;
                    }
                }
            }
            System.out.println("EDGES (comment_replyOf_comment): " + edgeCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process post_hasCreator_person file
        try {
            File file = new File(postCreator);
            Scanner scanner = new Scanner(file);
            int edgeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("Post.id")) {
                    String originalPostId = fila[0];
                    String originalPersonId = fila[1];
                    String newPostId = idMapping.get(originalPostId);
                    String newPersonId = idMapping.get(originalPersonId);
                    if (newPostId != null && newPersonId != null) {
                        String edge = newPostId + ",hasCreator," + newPersonId;
                        lineasEdges.add(edge);
                        edgeCount++;
                    }
                }
            }
            System.out.println("EDGES (post_hasCreator_person): " + edgeCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Process edges (person_likes_post)
        try {
            File file = new File(personLikesPost);
            Scanner scanner = new Scanner(file);
            int likesPostCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("Person.id")) {
                    likesPostCount++;
                    String originalPersonId = fila[0];
                    String originalPostId = fila[1];
                    String newPersonId = idMapping.get(originalPersonId);
                    String newPostId = idMapping.get(originalPostId);
                    if (newPersonId != null && newPostId != null) {
                        String edge = newPersonId + ",likes," + newPostId;
                        lineasEdges.add(edge);
                    }
                }
            }
            System.out.println("EDGES (person_likes_post): " + likesPostCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Process forum_containerOf_post file
        try {
            File file = new File(forumPost);
            Scanner scanner = new Scanner(file);
            int edgeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("Forum.id")) {
                    String originalForumId = fila[0];
                    String originalPostId = fila[1];
                    String newForumId = idMapping.get(originalForumId);
                    String newPostId = idMapping.get(originalPostId);
                    if (newForumId != null && newPostId != null) {
                        String edge = newForumId + ",containerOf," + newPostId;
                        lineasEdges.add(edge);
                        edgeCount++;
                    }
                }
            }
            System.out.println("EDGES (forum_containerOf_post): " + edgeCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
       // Process PersonhasInterest file
        try {
            File file = new File(personHasInterest);
            Scanner scanner = new Scanner(file);
            int edgeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("Person.id")) {
                    String personn = fila[0];
                    String tagg = fila[1];
                    String nuevoPersona = idMapping.get(personn);
                    String nuevoTag = idMapping.get(tagg);
                    if (nuevoPersona != null && nuevoTag != null) {
                        String edge = nuevoPersona + ",hasInterest," + nuevoTag;
                        lineasEdges.add(edge);
                        edgeCount++;
                    }
                }
            }
            System.out.println("EDGES (Interest): " + edgeCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        

       // Process forumHasTag file
        try {
            File file = new File(forumHasTag);
            Scanner scanner = new Scanner(file);
            int edgeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("Forum.id")) {
                    String originalCommentId = fila[0];
                    String originalPersonId = fila[1];
                    String newCommentId = idMapping.get(originalCommentId);
                    String newPersonId = idMapping.get(originalPersonId);
                    if (newCommentId != null && newPersonId != null) {
                        String edge = newCommentId + ",hasTag," + newPersonId;
                        lineasEdges.add(edge);
                        edgeCount++;
                    }
                }
            }
            System.out.println("EDGES (forumHasTag): " + edgeCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }   
        
       // Process postHasTag file
        try {
            File file = new File(postHasTag);
            Scanner scanner = new Scanner(file);
            int edgeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("Post.id")) {
                    String originalCommentId = fila[0];
                    String originalPersonId = fila[1];
                    String newCommentId = idMapping.get(originalCommentId);
                    String newPersonId = idMapping.get(originalPersonId);
                    if (newCommentId != null && newPersonId != null) {
                        String edge = newCommentId + ",hasTag," + newPersonId;
                        lineasEdges.add(edge);
                        edgeCount++;
                    }
                }
            }
            System.out.println("EDGES (postHasTag): " + edgeCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }         
 
       // Process commentHasTag file
        try {
            File file = new File(commentHasTag);
            Scanner scanner = new Scanner(file);
            int edgeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("Comment.id")) {
                    String originalCommentId = fila[0];
                    String originalPersonId = fila[1];
                    String newCommentId = idMapping.get(originalCommentId);
                    String newPersonId = idMapping.get(originalPersonId);
                    if (newCommentId != null && newPersonId != null) {
                        String edge = newCommentId + ",hasTag," + newPersonId;
                        lineasEdges.add(edge);
                        edgeCount++;
                    }
                }
            }
            System.out.println("EDGES (commentHasTag): " + edgeCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 

       // Process tagIsSubClassOf file
        try {
            File file = new File(tagIsSubClassOf);
            Scanner scanner = new Scanner(file);
            int edgeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("TagClass.id")) {
                    String originalCommentId = fila[0];
                    String originalPersonId = fila[1];
                    String newCommentId = idMapping.get(originalCommentId);
                    String newPersonId = idMapping.get(originalPersonId);
                    if (newCommentId != null && newPersonId != null) {
                        String edge = newCommentId + ",isSubclassOf," + newPersonId;
                        lineasEdges.add(edge);
                        edgeCount++;
                    }
                }
            }
            System.out.println("EDGES (isSubclassOf): " + edgeCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        // Archivos faltantes

        // comment_isLocatedIn_place
        try {
            File file = new File(basePath + File.separator + "dynamic" + File.separator + "comment_isLocatedIn_place_0_0.csv");
            Scanner scanner = new Scanner(file);
            int edgeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("Comment.id")) {
                    String originalCommentId = fila[0];
                    String originalPlace = fila[1];
                    String newCommentId = idMapping.get(originalCommentId);
                    String isPlace = idMapping.get(originalPlace);
                    //System.out.println(fila[0]+"  "+fila[1])
                    if (newCommentId != null && isPlace != null) {
                        String edge = newCommentId + ",isLocatedIn," + isPlace;
                        lineasEdges.add(edge);
                        edgeCount++;
                    }
                }
            }
            System.out.println("EDGES (comment_isLocatedIn_place): " + edgeCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // comment_replyOf_post
        try {
            File file = new File(basePath + File.separator + "dynamic" + File.separator + "comment_replyOf_post_0_0.csv");
            Scanner scanner = new Scanner(file);
            int edgeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("Comment.id")) {
                    String originalCommentId = fila[0];
                    String originalPost = fila[1];
                    String newCommentId = idMapping.get(originalCommentId);
                    String newPost = idMapping.get(originalPost);
                    //System.out.println(fila[0]+"  "+fila[1])
                    if (newCommentId != null && newPost != null) {
                        String edge = newCommentId + ",replyOf," + newPost;
                        lineasEdges.add(edge);
                        edgeCount++;
                    }
                }
            }
            System.out.println("EDGES (comment_replyOf_post): " + edgeCount);
            scanner.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }



        // person_isLocatedIn_place
        try {
            File file = new File(basePath + File.separator + "dynamic" + File.separator + "person_isLocatedIn_place_0_0.csv");
            Scanner scanner = new Scanner(file);
            int edgeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("Person.id")) {
                    String newPersonId = idMapping.get(fila[0]);
                    String newPlace = idMapping.get(fila[1]);
                    if (newPersonId != null && newPlace != null) {
                        String edge = newPersonId + ",isLocatedIn," + newPlace;
                        lineasEdges.add(edge);
                        edgeCount++;
                    }
                }
            }
            System.out.println("EDGES (person_isLocatedIn_place): " + edgeCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // person_studyAt_organisation
        try {
            File file = new File(basePath + File.separator + "dynamic" + File.separator + "person_studyAt_organisation_0_0.csv");
            Scanner scanner = new Scanner(file);
            int edgeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("Person.id")) {
                    String newPersonId = idMapping.get(fila[0]);
                    String newOrganisation = idMapping.get(fila[1]);
                    if (newPersonId != null && newOrganisation != null) {
                        String edge = newPersonId + ",studyAt," + newOrganisation;
                        lineasEdges.add(edge);
                        edgeCount++;
                    }
                }
            }
            System.out.println("EDGES (person_studyAt_organisation): " + edgeCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // person_workAt_organisation
        try {
            File file = new File(basePath + File.separator + "dynamic" + File.separator + "person_workAt_organisation_0_0.csv");
            Scanner scanner = new Scanner(file);
            int edgeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("Person.id")) {
                    String newPersonId = idMapping.get(fila[0]);
                    String newOrganisation = idMapping.get(fila[1]);
                    if (newPersonId != null && newOrganisation != null) {
                        String edge = newPersonId + ",workAt," + newOrganisation;
                        lineasEdges.add(edge);
                        edgeCount++;
                    }
                }
            }
            System.out.println("EDGES (person_workAt_organisation): " + edgeCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // place_isPartOf_place
        try {
            File file = new File(basePath + File.separator + "static" + File.separator + "place_isPartOf_place_0_0.csv");
            Scanner scanner = new Scanner(file);
            int edgeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("Place.id")) {
                    String newPlaceId = idMapping.get(fila[0]);
                    String parentPlace = idMapping.get(fila[1]);
                    if (newPlaceId != null && parentPlace != null) {
                        String edge = newPlaceId + ",isPartOf," + parentPlace;
                        lineasEdges.add(edge);
                        edgeCount++;
                    }
                }
            }
            System.out.println("EDGES (place_isPartOf_place): " + edgeCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // tag_hasType_tagclass
        try {
            File file = new File(basePath + File.separator + "static" + File.separator + "tag_hasType_tagclass_0_0.csv");
            Scanner scanner = new Scanner(file);
            int edgeCount = 0;
            while (scanner.hasNextLine()) {
                String[] fila = scanner.nextLine().split("\\|");
                if (fila.length > 1 && !fila[0].equals("Tag.id")) {
                    String newTagId = idMapping.get(fila[0]);
                    String tagClass2 = idMapping.get(fila[1]);
                    if (newTagId != null && tagClass2 != null) {
                        String edge = newTagId + ",hasType," + tagClass2;
                        lineasEdges.add(edge);
                        edgeCount++;
                    }
                }
            }
            System.out.println("EDGES (tag_hasType_tagclass): " + edgeCount);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

     
        // Write nodes to file
        escribirCSV(nuevoCSV, lineasNodes);
        System.out.println("total de nodos: " + lineasNodes.size());
        System.out.println("total de edges: " + lineasEdges.size());
        // Write edges to file
        escribirCSV(edges, lineasEdges);
        //System.out.println(idMapping);
    }

    private static void escribirCSV(String archivo, List<String> lineas) {
        try {
            FileWriter writer = new FileWriter(archivo);
            for (String linea : lineas) {
                writer.write(linea);
                writer.write("\n");
            }
            writer.close();
            System.out.println("archivo generado: " + archivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

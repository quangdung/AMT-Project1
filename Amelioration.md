Amélioration par rapport à l'étape 1 du projet en AMT
============

Auteurs : Nguyen-Phuong Le & Quang-Dung Ngo

---

# Summary

- [Repo GitHub](#Repo_GitHub) <a id="Repo_GitHub_s"></a>
- [API REST](#API_REST) <a id="API_REST_s"></a>
- [Installation / premiers pas et Code](#autre_correction) <a id="autre_correction_s"></a>

---

# Repo GitHub <a id="Repo_GitHub"></a>

>- Il y a de la matière, mais ça mérite d'être restructuré et mis en contexte. Un développeur qui arrive sur le repo et n'a pas le contexte du projet va être perdu et ne va probablement pas avoir envie d'essayer votre service. Vous devez ajouter une introduction pour expliquer ce que fait votre plate-forme et DONNER ENVIE au visiteur d'aller plus loin (i.e. d'installer la plate-forme et de la tester).

>- C'est bien d'avoir un diagramme pour présenter le modèle métier, mais il faut ajouter du TEXTE pour le décrire. Cette description est aussi très utile quand on veut découvrir votre API.

> - Le paragraphe "Implementation choices" est à discuter. Sur le principe, c'est très bien de l'avoir mis. Maintenant, si il n'y a qu'un seul choix, on peut se poser la question. On pourrait imaginer de le renommer "Implementation" et ajouter une description de l'architecture, des différentes tiers, etc.

> - Il serait utile d'ajouter un lien vers votre API en ligne!

> - Point important: il n'y a aucune marche à suivre, procédure de validation. Comment va-t-on pouvoir tester votre plate-forme et vérifier qu'elle fait son travail correctement? Vous mentionnez une génération de données dans "Known issues", mais c'est tout...

Corrigé, dans `Readme.md`

[(en haut)](#Repo_GitHub_s)

---

# API REST <a id="API_REST"></a>

>- Bien d'avoir mis le contenu sur la page "Home" - cela répond en partie à mes questions sur le point "Repo GitHub". Bien également d'avoir adapté le contenu des autres pages par rapport à votre implémentation.

Corrigé, dans `RAML`

---

>- GET /organizations et /organizations/id. Il n'y a pas de personne de contact dans le payload de la réponse - est-ce que vous ne gérez pas du tout cet aspect?

Corrigé

- `GET /organizations/{id}/contact` retourne la personne de contact de l'organisation `{id}`, géré par `OrganizationRessource`. 

- `GET /organizations/{id}/users` retourne tous les users de l'organisation `{id}`, géré par `OrganizationRessource`


----------

>- POST /organizations. Attention: le payload dans la réponse n'est pas du json valide! Notez qu'une pratique recommandée est de retourner un 201 (ce que vous faites) + une entête "Location" avec l'URI de la ressource qui vient d'être créée (/organizations/119). Certaines APIs renvoient aussi une représentation complète de la ressource dans la réponse - ça peut parfois se justifier, si il y a beaucoup de champs CALCULES par le serveur au moment de la création et qu'on veut informer le client sans qu'il doive tout de suite faire un GET après un POST.

Corrigé

- Retouner le json complet de la nouvelle organisation avec son numéro `id`. 

- Raison du choix au lieu de renvoyer le payload avec l'entête "location" : 
	- Pour éviter de vérifier le contenu créé par un GET après
	- Le servlet d'intiation `/api/InitData` est implémenté en récupérant directement le champ `id` dans le json retourné

---

> - PUT /organizations/id. Pourquoi n'avez-vous pas mis de réponse dans la doc? Même commentaire pour le DELETE (et pour les autres ressources)

Corrigé dans plusieurs ressources : `sensors`, `organizations`, `users`, `observations`

- Une information `PUT` ou `DELETE` est maintenant retournée avec le code de status `OK 200`. La documentation **REST API** est mise à jour

- Choix d'implémentation : ne pas retourner le contenu modifié ou supprimé

---

>- GET /users/. Vous retournez le mot de passe dans le payload!!! Vous retournez l'organisation dans un sous-objet JSON. Pourquoi pas (il n'y a de toute manière qu'un champs), mais ça suscite ma curiosité par rapport à l'implémentation de vos DTOs - on verra ça en regardant le code.

Corrigé, il n'y a plus de champ `passowrd` dans le payload (géré par `UserRessource`)

---

>- PUT /users/id. Ici aussi, vous envoyez l'organisation comme un sous-objet JSON et dans ce cas j'ai beaucoup plus de peine. Que devrait-il se passer si j'envoie un id et un nom d'organisation qui ne correspondent pas à ce qu'il y a dans la DB? Une erreur ou une modification de l'état de l'organisation? (ce qui serait à mon avis un mauvais choix). Personnellement, j'enlèverais carrément la partie organisation dans le DTO pour le PUT. Si on veut vraiment avoir la possibilité de déplacer un utilisateur d'une organisation à l'autre, je ferais un endpoint spécial pour ça.

Corrigé

- Enlever les "sous-objets" dans les réponses de `Organization`
- Corriger au niveau DTO dans `UserDTO` et `UserRessource` : il n'y a que `orgId` dans la réponse
- Possible de changer l'oganisation de `user` avec `PUT` en modifiant ce champ `orgId`

---

>- POST /observations. En regardant le payload, mes inquiétudes se confirment par rapport la conception de vos DTO. Là, c'est clairement une mauvaise idée d'envoyer toutes infos concernant les capteurs et les orgnisations dans le payload! Non seulement ce n'est pas utile et ça consomme de la bande passante pour rien, mais en plus ça crée un couplage fort entre le client et le serveur (au niveau des données). Tout ce qu'un capteur doit connaître pour envoyer des observations, c'est un timestamp, une mesure et un id de capteur. C'est ce qu'on doit trouver dans le payload. Tout le reste (les associations) doit être géré du côté serveur! Il faudra absolument changer cette partie.

Corrigé

- Corriger le DTO de `Observation` dans `ObservationDTO`, `ObservationRessource` (méthode `toDTO`, `toObservation`)
- Corriger la méthode `POST` de `ObservationRessource` pour retourner tout le nouveau contenu
- Corriger aussi le même problème de `Sensor`

---

>- GET /observations. Si vous exposez cette URL, vous êtes obligés d'archiver TOUTES les observations. Vous devez réaliser que cela vous obligera à mettre en place une infrastructure très coûteuse. Répondez-vous vraiment à un besoin du client? Sera-t-il prêt à payer le prix supplémentaire?

Corrigé

- Enlever les deux méthodes `GET` dans `ObservationRessource`

---

>- GET /facts. D'après la documentation et le payload, très difficile de savoir comment je vais pouvoir écrire mon client. Vous ne parlez pas des différents types de facts. Que va-t-il se passer quand on aura différentes propriétés pour un autre type de fact?

Corrigé

- Corriger dans les fichiers `Fact` <- `FactTiedToSensor` <- `FactTiedToDate` : modifier les constructeurs
- Ajouter le fichier `FactType` pour définir les 2 types (avec des constantes String)
- Corriger dans `ObservationRessource`, méthode `POST {id}`
- Avoir essayé d'enlever la propriété/la colonne `type` car il existe déjà la colonne DiscriminatorColumn `typeOfFact`, mais la gestion des DTO s'avèrent compliquée car cette colonne `typeOfFact` n'est pas lue correctement par les requêtes. Donc, il y a deux colonnes qui décrivent le type de fact `typeOfFact` (gérée par le système) et `type` (implémenté pour pouvoir identifier les fact)
- Corriger les problèmes de **JOIN** `Organization` et `Sensor` dans `FactDTO`, `FactTiedToSensorDTO`
- La documentation `REST API` est mise à jour

---

>- DELETE /facts. Est-ce que vous voulez vraiment autoriser la suppression de facts? Je serais partisan de l'approche où les facts sont disponibles en lecture seule (ils sont créés et modifiés par le serveur).

Corrigé

- Supprimer la méthode `DELETE` et `PUT`

---

Mieux gérér le type de date `java.util.Date` :

	
	1. Ajouter un adapteur
	----------------------
	package ch.heigvd.amt_project.dto;
	
	import java.text.SimpleDateFormat;
	import java.util.Date;
	 
	import javax.xml.bind.annotation.adapters.XmlAdapter;
	 
	public class DateAdapter extends XmlAdapter<String, Date> {
	 
	    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	   
	
	    @Override
	    public String marshal(Date date) throws Exception {
	      String result = dateFormat.format(date);
	        return result;
	    }
	 
	    @Override
	    public Date unmarshal(String string) throws Exception {
	      Date d = dateFormat.parse(string);
	      return dateFormat.parse(string);
	    }
	 
	}
	
	
	2. Utiliser l'adapteur dans le DTO (ObservationDTO)
	----------------------------------
	    @XmlJavaTypeAdapter(DateAdapter.class)
	    public Date getCreationDate() {
	        return creationDate;
	    }
	
	
	
	3. Ne pas utiliser java.sql.Date, mais java.util.Date (ça m'a fait perdre plusieurs heures...)
	
	4. Dans vos entités JPA, il faut rajouter l'annoation @Temporal sur tous les champs de type date
	
[(en haut)](#API_REST_s)

---

# Installation / premiers pas et Code <a id="autre_correction"></a>

Faute de temps, les parties 3 et 4 des remarques sont partiellement corrigées

	3. Installation / premiers pas
	------------------------------
	
	- Pas de soucis au déploiement. C'est bien d'avoir modifié la page d'index et d'orienter l'utilisateur vers la procédure de génération de données. Mais allez plus loin! Mettez en place un template bootstrap, donnez des infos sur le projet, expliquez en quoi va consister la procédure de génération de données!
	
	- La génération semble avoir fonctionné... mais que dois-je faire maintenant?
	
	- En regardant la structure et le contenu de la db, je marque que les mots de passe sont stockés en clair, qu'il y aurait une gestion des personnes de contact (mais comme je n'ai rien vu dans l'API à ce sujet... comment est-ce qu'une personne devient personne de contact?). La table Facts me plaît bien: je vais pouvoir faire des requêtes SQL qui exploitent les attributs des différents types de fact. Mieux que certains groupes qui ont utilisé une colonne de type texte/blob dans laquelle ils ont concaténé les attributs.
	
	- Pour ma première requête REST, je constate que l'URI décrite dans la doc ne correspond pas à celle dans le code. Je suis obligé d'aller regarder dans ApplicationConfig. Dans mon cas, ce n'est pas dramatique... mais un développeur évaluant votre service n'aurait pas pris cette peine!
	
	- Je teste quelques GET. Les payloads correspondent à ce qui est décrit dans la doc. Sauf que pour les facts, je n'obtiens pas les propriétés (count, min, max, avg)!!! Pas très utile pour écrire mon client!!!
	
	- En faisant un POST sur /organizations, ça passe - mais je confirme que la réponse n'est pas du json valide.
	
	- Je fais un POST sur /observations, en faisant un copier-coller de votre example. Boum! Une erreur 500 sur "During synchronization a new object was found through a relationship that was not marked cascade PERSIST: Sensor #118, firstsensor, firstsensor, firstsensor, , firstorg, public." Non seulement cette partie de l'API n'est pas du tout pratique à utiliser, mais en plus l'implémentation n'est pas correcte. Simplifiez!
	

	4. Code
	-------
	
	- Gros soucis dans les DTOs. D'abord, il faut vraiment que vous vous posiez la question de savoir si vous voulez envoyer (dans le cas d'une association entre 2 objets): un sous-objet json OU un id OU une url:
	
	{
	  "org" : {
	     "name" : "heig-vd"
	   }
	}
	
	{
	  "orgId" : 82
	}
	
	{
	  "orgUrl" : /api/organizatoins/82
	}
	
	Choisissez le sous-objet si vous pensez que le client va presque tout le temps avoir besoin de l'objet lié en même temps que l'objet principal (pour éviter de devoir faire 2 requêtes). Probablement sage d'éviter ça dans les listes. Entre les id et les URLs, de plus en plus d'API préfèrent envoyer une URL (c'est un style appelé HATEOAS) car cela réduit le couplage entre le client et le serveur (on ne doit pas hardcoder les endpoints dans le client).
	
	- Ensuite, vous ne pouvez pas mélanger DTO et Entités JPA, c'est super dangereux (références ciculaires, etc.). Donc, si vous avez un UserDTO, il doit utilier un OrganizationDTO et pas une Organization!!
	
	- Dans le endpoint /facts, comme vous utilisez FactDTO dans la signature, il n'y a que les attributs définis dans la classe abstraite qui sont sérialisés (d'où la différence entre la documentation d'API et l'implémentation). Au lieu de définir un DTO par type de fact, je ferais un seul DTO (FactDTO) avec une liste dynamique de propriétés, avec quelque chose du genre:
	
	{
	  "type" : "counter",
	  "properties" : {
	    "sensor" : "/api/sensors/89",
	    "total" : 82
	  }
	}
	
	{
	  "type" : "aggregate",
	  "properties" : {
	    "sensor" : "/api/sensors/89",
	    "date" : "2014-12-31",
	    "avg" : 34.4,
	    "min" : 22.9
	  }
	}
	
	- Dans FactTiedToDate, cette named query me semble suspecte (parce que vous utilisez Fact...)
	@NamedQuery(
	    name = "FactTiedToDate.findAll",
	    query = "SELECT f FROM Fact f WHERE f.type = :type"
	),
	
	- Lors de la création d'une observation, votre code n'est pas du tout efficace. Vous récupérez la liste de tous les facts et vous itérez dessus pour tester si l'id de capteur correspond. Remplacez cette logique par un lookup de fact par id de capteur (named query)!
	
	- Pour le traitement des observations et le calcul des facts, je properais la structure suivante (en introduisant une couche "business"). Les DAOs ne s'occupent que du CRUD. Les ressources JAX-RS ne s'occupent que du routing des requêtes vers les appels de méthodes. Vous introduisez un service "ObservationsFlowProcessor" qui contient la logique métier pour calculer les facts:
	
	Client --> api.ObservationResource
	               --> services.dao.ObservationsManager (on ne fait qu'archiver les observations)
	               --> services.business.ObservationsFlowProcessor (on calcule)
	                   ---> services.dao.FactsManager (on archive/met à jour des facts)
	
	- Il y a des entêtes (commentaires) générés par l'IDE qui traînent...

[(en haut)](#autre_correction_s)


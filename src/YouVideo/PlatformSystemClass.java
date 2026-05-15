package YouVideo;

import Exceptions.*;

import java.util.*;


/**
 * Implementation of the YouVideo platform system.
 * Maintains four internal collections: all videos (publishable and episodes),
 * all podcasts, all known authors, and all shows.
 * All map keys are normalized to uppercase to ensure case-insensitive access.
 */
public class PlatformSystemClass implements PlatformSystem {
    private static final int MAX_VIDEOS = 2000;
    private static final int MAX_PODS_SHOWS_AUTHORS = 500;

    // All videos registered in the system, including publishable videos and episodes.
    private Map<String, Video> videos;
    // All podcasts registered in the system, in insertion order.
    private Map<String, Podcast> podcasts;
    // All authors known to the system (from podcasts and shows), in insertion order.
    private Map<String, Author> authors;
    // All shows registered in the system, in insertion order.
    private Map<String, Show> shows;

    private Map<String,SortedSet<TaggedContent> >taggedContents;
    // Map linking a Tag to a list of Podcasts that have it.
    private Map<String, SortedSet<Podcast>> podcastsByTag;
    // Map linking a Tag to a list of Shows that have it.
    private Map<String, SortedSet<Show>> showsByTag;

    /**
     * Creates an empty platform with no content.
     */
    public PlatformSystemClass() {
        videos        = new HashMap<>(MAX_VIDEOS);
        podcasts      = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);
        authors       = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);
        shows         = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);
        podcastsByTag = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);
        showsByTag    = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);
        taggedContents = new HashMap<>(MAX_PODS_SHOWS_AUTHORS);
    }

    /**
     * Normalizes a map key to uppercase for case-insensitive access.
     * @param key the original key string.
     * @return the key in uppercase.
     */
    private String normalizeKey(String key) {
        return key.toUpperCase();
    }


    // ----------------------------- VIDEO -----------------------------

    @Override
    public void addPublishable(String id, int duration, String url, String publisher,
                               String title, Locale lang)
            throws PublishableAlreadyExistsException {
        String idKey = normalizeKey(id);

        if (videos.containsKey(idKey))
            throw new PublishableAlreadyExistsException();
        videos.put(idKey, new BasicVideoClass(id, duration, url, publisher, title, lang));
    }

    @Override
    public void addPremiumPublishable(String id, int duration, String url,
                                      String publisher, String title, Locale lang,
                                      String subtitleUrl, Locale subtitleLang)
            throws PublishableAlreadyExistsException {
        String idKey = normalizeKey(id);

        if (videos.containsKey(idKey))
            throw new PublishableAlreadyExistsException();
        videos.put(idKey, new PremiumVideoClass(id, duration, url,
                publisher, title, lang, subtitleUrl, subtitleLang));
    }

    @Override
    public Video getVideo(String id)
            throws PublishableNotExistsException, IsEpisodeException {
        String idKey = normalizeKey(id);

        Video video = videos.get(idKey);
        if (video == null)
            throw new PublishableNotExistsException();
        if (video instanceof Episode)
            throw new IsEpisodeException();
        return video;
    }

    @Override
    public void addSubtitle(String id, String subtitleUrl, Locale subtitleLang)
            throws PublishableNotExistsException, NotAPremiumVideoException {
        String idKey = normalizeKey(id);

        Video video = videos.get(idKey);
        if (video == null)
            throw new PublishableNotExistsException();
        if (!(video instanceof PremiumVideo))
            throw new NotAPremiumVideoException();
        ((PremiumVideo) video).addSubtitle(subtitleUrl, subtitleLang);
    }

    @Override
    public Iterator<Subtitle> subtitleIterator(String id) throws
            PublishableNotExistsException, IsEpisodeException, NotAPremiumVideoException {
        String idKey = normalizeKey(id);

        Video video = videos.get(idKey);
        if (video == null)
            throw new PublishableNotExistsException();
        else if (video instanceof Episode)
            throw new IsEpisodeException();
        else if (!(video instanceof PremiumVideo))
            throw new NotAPremiumVideoException();
        return ((PremiumVideo) video).subtitleIterator();
    }

    @Override
    public void removeVideo(String id) throws PublishableNotExistsException,
            IsEpisodeException, VideoUsedInShowException {
        String idKey = normalizeKey(id);

        Video video = videos.get(idKey);
        if (video == null)
            throw new PublishableNotExistsException();
        if (video instanceof Episode)
            throw new IsEpisodeException();
        if (shows.containsKey(normalizeKey(((PublishableVideo) video).getTitle())))
            throw new VideoUsedInShowException();
        videos.remove(idKey);
    }


    // ----------------------------- PODCAST -----------------------------

    @Override
    public void addPodcast(String title, String author, Locale lang)
            throws PodcastAlreadyExistsException {
        String titleKey  = normalizeKey(title);
        String authorKey = normalizeKey(author);

        if (podcasts.containsKey(titleKey))
            throw new PodcastAlreadyExistsException();

        Author au;
        if (authors.containsKey(authorKey)) {
            au = authors.get(authorKey);
        } else {
            au = new AuthorClass(author);
            authors.put(authorKey, au);
        }

        Podcast podcast = new PodcastClass(title, au.getAuthor(), lang);
        podcasts.put(titleKey, podcast);
        ((AuthorAll) au).addPodcast(podcast);
    }

    @Override
    public void addEpisode(String title, String id, int duration, String url, String date)
            throws PodcastNotExistsException,
            EpisodeAlreadyExistsException, InvalidEpisodeDateException {
        String titleKey = normalizeKey(title);
        String idKey    = normalizeKey(id);

        if (!podcasts.containsKey(titleKey))
            throw new PodcastNotExistsException();
        if (videos.containsKey(idKey))
            throw new EpisodeAlreadyExistsException();

        PodcastAll podcast = (PodcastAll) podcasts.get(titleKey);
        if (!podcast.isValidEpisodeDate(date))
            throw new InvalidEpisodeDateException();

        Episode episode = new EpisodeClass(id, duration, url, date);
        videos.put(idKey, episode);
        podcast.addEpisode(episode);
    }

    @Override
    public Podcast getPodcast(String title)
            throws PodcastNotExistsException {
        String titleKey = normalizeKey(title);

        if (!podcasts.containsKey(titleKey))
            throw new PodcastNotExistsException();
        return podcasts.get(titleKey);
    }

    @Override
    public Iterator<Episode> episodeIterator(String title) throws PodcastNotExistsException {
        String titleKey = normalizeKey(title);

        if (!podcasts.containsKey(titleKey))
            throw new PodcastNotExistsException();
        return podcasts.get(titleKey).episodeIterator();
    }

    @Override
    public Iterator<Podcast> authorPodcast(String author) throws PodcastNotFoundException {
        String authorKey = normalizeKey(author);

        Author au = authors.get(authorKey);
        if (au == null || au.isPodcastsEmpty())
            throw new PodcastNotFoundException();
        return au.podcastIterator();
    }

    @Override
    public void removePodcast(String podcastTitle) throws PodcastNotExistsException {
        String titleKey = normalizeKey(podcastTitle);

        if (!podcasts.containsKey(titleKey))
            throw new PodcastNotExistsException();

        Podcast podcast = podcasts.get(titleKey);

        Iterator<Episode> it = podcast.episodeIterator();
        while (it.hasNext()) {
            Episode ep = it.next();
            videos.remove(normalizeKey(ep.getId()));
        }

        Author author = authors.get(normalizeKey(podcast.getAuthor()));
        ((AuthorAll) author).removePodcast(podcast);
        podcasts.remove(titleKey);
    }


    // ----------------------------- SHOW -----------------------------

    // se possivel separar esse codigo em metodos auxiliares
    @Override
    public void addShow(String showAuthor, String videoID, String date)
            throws PublishableNotExistsException, ShowAlreadyExistsException {
        String idKey     = normalizeKey(videoID);
        String authorKey = normalizeKey(showAuthor);

        Video v = videos.get(idKey);
        if (v == null || v instanceof Episode)
            throw new PublishableNotExistsException();

        PublishableVideo video = (PublishableVideo) v;
        String titleKey = normalizeKey(video.getTitle());

        if (shows.containsKey(titleKey))
            throw new ShowAlreadyExistsException();

        Author au;
        if (authors.containsKey(authorKey)) {
            au = authors.get(authorKey);
        } else {
            au = new AuthorClass(showAuthor);
            authors.put(authorKey, au);
        }

        Show show = new ShowClass(au.getAuthor(), date, video.getTitle(), video);
        shows.put(titleKey, show);
        ((AuthorAll) au).addShow(show);
    }

    @Override
    public Show getShow(String title) throws ShowNotExistsException {
        String titleKey = normalizeKey(title);

        if (!shows.containsKey(titleKey))
            throw new ShowNotExistsException();
        return shows.get(titleKey);
    }

    @Override
    public void removeShow(String showTitle) throws ShowNotExistsException {
        String titleKey = normalizeKey(showTitle);

        if (!shows.containsKey(titleKey))
            throw new ShowNotExistsException();

        Show show = shows.get(titleKey);
        Author author = authors.get(normalizeKey(show.getAuthor()));
        ((AuthorAll) author).removeShow(show);
        shows.remove(titleKey);
    }

    @Override
    public Iterator<Show> authorShows(String author) {
        String authorKey = normalizeKey(author);
        Author au = authors.get(authorKey);
        if (au == null || au.isShowsEmpty()){
            return Collections.emptyIterator();
        }
        return au.showIterator();
    }


    // ----------------------------- TAGS -----------------------------

    // se possivel minimizar esse metodo em metodos auxiliares

    @Override
    public void addTag(String title, String tag)
            throws TitleAlreadyTaggedException, TitleDoesNotExistException {
        String titleKey = normalizeKey(title);
        String tagKey   = normalizeKey(tag);

        Podcast podcast = podcasts.get(titleKey);
        Show  show = shows.get(titleKey);

        if (podcast == null && show == null)
            throw new TitleDoesNotExistException();

        //pega a list de objetos associados a tag , caso a tag não existir agente cria uma com a list(SorteSet) ordenada pelas tags
        SortedSet<TaggedContent> contents = taggedContents.get(tagKey);
        if (contents == null){
            contents = new TreeSet<>(new ComparatorByTags());
            taggedContents.put(tagKey,contents);//bola a tag como chave associado a lista no criada
        }

        if (podcast != null) {
             podcast.addTag(tag);

            SortedSet<Podcast> podcastList = podcastsByTag.get(tagKey);
            if (podcastList == null) {
                podcastList = new TreeSet<>(new ComparatorPodcastByTitles());//cria uma lista de podcast dessa tag ordenada por titulo
                podcastsByTag.put(tagKey, podcastList);
            }
            podcastList.add(podcast);
            //adiciona o podcast a list da tag associada , que sera uma lista geral de tudo podcast e shows
            contents.add(podcast);

        }

        if (show != null) {
            show.addTag(tag);
            SortedSet<Show> showList = showsByTag.get(tagKey);
            if (showList== null) {
                showList = new TreeSet<>(new ComparatorShowByTitles());//cria uma lista de shows dessa tag ordena por titulo
                showsByTag.put(tagKey, showList);
            }
            showList.add(show);

            contents.add(show);
        }

    }

    // se possivel minimizar esse metodo em metodos auxiliares e cria variaveis locais para os gets
    @Override
    public void removeTag(String title, String tag)
            throws TitleDoesNotExistException, TitleIsNotTaggedException {
        String titleKey = normalizeKey(title);
        String tagKey   = normalizeKey(tag);

        Podcast podcast = podcasts.get(titleKey);
        Show show = shows.get(titleKey);

        if (podcast == null && show == null)
            throw new TitleDoesNotExistException();

        boolean wasRemoved = false;
// verifica se é null ou se ja foi removida o metodo remove retorna boolean (True se for removida, False se não)
        if( (podcast != null) && (podcast.removeTag(tag))) {
            if (taggedContents.containsKey(tagKey)){
                taggedContents.get(tagKey).remove(podcast);
            }
            podcastsByTag.get(tagKey).remove(podcast);
            wasRemoved = true;
        }
// verifica se é null ou se ja foi removida o metodo remove retorna boolean (True se for removida, False se não)
        if ((show != null) && ( show.removeTag(tag))) {
            if (taggedContents.containsKey(tagKey)){//caso a tag ja existe no sistema onde tem todas as tags ele remove esse show da tag
                taggedContents.get(tagKey).remove(show);
            }
            //remove da coleção de shows com tags
            showsByTag.get(tagKey).remove(show);
            wasRemoved = true;
        }

        // caso no fim se o não foi possivel remover é porque não existe
        if (!wasRemoved)
            throw new TitleIsNotTaggedException();
    }

    @Override
    public Iterator<Author> authorsProductivity() {
        Set<Author> authorsProductivity = new TreeSet<>(new ComparatorByProductivity());
        for (Author author : authors.values()){
            if (author.getProductivity() > 0){
                authorsProductivity.add(author);
            }
        }
        return authorsProductivity.iterator();
    }

    @Override
    public Iterator<TaggedContent> listAscAll(String tag){
        String tagKey = normalizeKey(tag);
       SortedSet<TaggedContent> contents = taggedContents.get(tagKey);
       if (contents == null){
           return Collections.emptyIterator();
       }
       return contents.iterator();
    }

    @Override
    public Iterator<TaggedContent> listDesAll(String tag){
        String tagKey = normalizeKey(tag);

        // ele vai ordenar esse SortedSet pelo descendente
        SortedSet<TaggedContent> taggedContentDes = new TreeSet<>(new ComparatorByTagsDesc());

        SortedSet<TaggedContent> Contents = taggedContents.get(tagKey); // pega a coleção geral de objetos dessa taga

        if (Contents == null){
            return Collections.emptyIterator();
        }

        //adiciona abjetos por orden descendente
        Iterator<TaggedContent> it = Contents.iterator();
        while (it.hasNext()){
            TaggedContent objet = it.next();
            taggedContentDes.add(objet);
        }
        return taggedContentDes.iterator();

    }

    @Override
    public Iterator<Podcast> listAscPodcast(String tag) {
        String tagKey = normalizeKey(tag);
        SortedSet<Podcast> podcastList = podcastsByTag.get(tagKey);
        if (podcastList == null ){
            return Collections.emptyIterator();
        }
       return podcastList.iterator();
    }

    @Override
    public Iterator<Show> listAscShow(String tag) {
        String tagKey = normalizeKey(tag);
        SortedSet<Show> showList = showsByTag.get(tagKey);
        if ( showList == null ){
            return Collections.emptyIterator();
        }
        return showList.iterator();
    }

    @Override
    public Iterator<Podcast> listDesPodcast(String tag) {
        String tagKey = normalizeKey(tag);
        List<Podcast> podcastListDes = new LinkedList<>();
        SortedSet <Podcast> podcastList = podcastsByTag.get(tagKey);
        if (podcastList == null){
            return Collections.emptyIterator();
        }
        for (Podcast podcast : podcastList){
            podcastListDes.addFirst(podcast);
        }
        return podcastListDes.iterator();

    }

    @Override
    public Iterator<Show> listDesShow(String tag) {
        String tagKey = normalizeKey(tag);
        List<Show> showListDes = new LinkedList<>();
        SortedSet<Show> showList = showsByTag.get(tagKey); // é um SortedSet porque tem que ordenar por ordem alfabetica
        if (showList == null){
            return Collections.emptyIterator(); // retorna um iterador vazio caso for nulo
        }
        for (Show show : showList){
            showListDes.addFirst(show); // fica adicionando sempre na primeira possisão
        }
        return showListDes.iterator();
    }
}
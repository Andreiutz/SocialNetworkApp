package com.example.socialnetworkgui.service;


import com.example.socialnetworkgui.domain.CommunityNode;
import com.example.socialnetworkgui.domain.OrderedStringPair;
import com.example.socialnetworkgui.domain.friendship.FriendRequest;
import com.example.socialnetworkgui.domain.friendship.Friendship;
import com.example.socialnetworkgui.domain.friendship.Status;
import com.example.socialnetworkgui.domain.user.Address;
import com.example.socialnetworkgui.domain.user.User;
import com.example.socialnetworkgui.domain.user.UserParameter;
import com.example.socialnetworkgui.domain.validators.FriendRequestValidator;
import com.example.socialnetworkgui.domain.validators.FriendshipValidator;
import com.example.socialnetworkgui.domain.validators.UserValidator;
import com.example.socialnetworkgui.repository.Repository;
import com.example.socialnetworkgui.repository.database.FriendRequestsDbRepository;
import com.example.socialnetworkgui.repository.database.FriendshipDbRepository;
import com.example.socialnetworkgui.repository.database.UserDbRepository;
import com.example.socialnetworkgui.utils.events.ChangeEventType;
import com.example.socialnetworkgui.utils.events.EntityChangedEvent;
import com.example.socialnetworkgui.utils.observer.Observable;
import com.example.socialnetworkgui.utils.observer.Observer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service used for managing the SocialNetwork
 */
public class Service implements Observable<EntityChangedEvent> {

    private Repository<String, User> usersRepo;
    private Repository<OrderedStringPair, Friendship> friendshipsRepo;
    private Repository<Integer, FriendRequest> friendRequestsRepo;
    private static User currentLoggedUser;
    private final List<Observer<EntityChangedEvent>> observers = new ArrayList<>();
    private static final Service singletonService = new Service(
            new UserDbRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres", new UserValidator()),
            new FriendshipDbRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres", new FriendshipValidator()),
            new FriendRequestsDbRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres", new FriendRequestValidator())
    );

    private Service() {}

    private Service(Repository<String, User> usersRepo, Repository<OrderedStringPair, Friendship> friendshipsRepo, Repository<Integer, FriendRequest> friendRequestsRepo) {
        this.usersRepo = usersRepo;
        this.friendshipsRepo = friendshipsRepo;
        this.friendRequestsRepo = friendRequestsRepo;
    }

    public static Service getInstance() {
        return singletonService;
    }

    public static User getCurrentLoggedUser() {
        return currentLoggedUser;
    }
    public static void setCurrentLoggedUser(User user) {
        currentLoggedUser = user;
    }


    /**
     * Adds a new user
     *
     * @param firstName, String
     * @param lastName,  String
     * @param userName,  String
     * @throws ServiceException, if the user was not added
     */
    public void addUser(String userName, String firstName, String lastName, String email, LocalDate date, Address address, String password) {
        User newUser = new User(userName, firstName, lastName, email, date, address, password);
        if (!usersRepo.save(newUser)) {
            throw new ServiceException("User could not be saved!");
        }
    }

    /**
     * Updates an existing user, or throws error is user is not found
     * @param userName id
     * @param newParam new value
     * @param parameter the field which is updated
     */
    public void updateUser(String userName, String newParam, UserParameter parameter) {
        User user = usersRepo.findOne(userName);
        if (user == null) {
            throw new ServiceException("User not found!");
        }
        switch (parameter) {
            case FIRST_NAME -> user.setFirstName(newParam);
            case LAST_NAME -> user.setLastName(newParam);
            case EMAIL -> user.setEmail(newParam);
            case COUNTRY -> {
                Address newAddress = user.getAddress();
                newAddress.setCountry(newParam);
                user.setAddress(newAddress);
            }
            case COUNTY -> {
                Address newAddress = user.getAddress();
                newAddress.setCounty(newParam);
                user.setAddress(newAddress);
            }
            case CITY -> {
                Address newAddress = user.getAddress();
                newAddress.setCity(newParam);
                user.setAddress(newAddress);
            }
            case STREET -> {
                Address newAddress = user.getAddress();
                newAddress.setStreet(newParam);
                user.setAddress(newAddress);
            }
            case STREET_NUMBER -> {
                Address newAddress = user.getAddress();
                try {
                    newAddress.setStreetNumber(Long.parseLong(newParam));
                } catch (NumberFormatException exception) {
                    newAddress.setStreetNumber(-1L);
                }
            }
            default -> throw new ServiceException("Invalid user parameter!");
        }
        if (!usersRepo.update(user)) {
            throw new ServiceException("User could not be updated!");
        }
    }

    /**
     * Removes a user
     *
     * @param userName, String
     * @throws ServiceException, if the user was not found
     */
    public void removeUser(String userName) {
        CommunityNode communityNode = getUserWithFriends(userName);
        if (communityNode == null) {
            throw new ServiceException("User does not exist!");
        }
        for (User friend : communityNode.getFriends()) {
            friendshipsRepo.delete(new OrderedStringPair(userName, friend.getId()));
        }
        usersRepo.delete(userName);

    }

    /**
     * @return The list with all the Users
     */
    public Iterable<User> getAllUsers() {
        return usersRepo.findAll();
    }

    public User findUser(String userName) {
        return usersRepo.findOne(userName);
    }

    /**
     * Adds a new friendship
     * @param userName1, String
     * @param userName2, String
     * @throws ServiceException, if the friendship could not be established
     */
    public void addFriendShip(String userName1, String userName2) {
        User user1 = usersRepo.findOne(userName1);
        User user2 = usersRepo.findOne(userName2);
        if (user1 == null || user2 == null) {
            throw new ServiceException("Unable to create friendship! At least one user does not exist!");
        }
        Friendship newFriendship = new Friendship(userName1, userName2);
        if (!friendshipsRepo.save(newFriendship)) {
            throw new ServiceException("Failed to save friendship! It already exists!");
        }
        notifyObservers(new EntityChangedEvent(ChangeEventType.ADD, newFriendship));
    }

    /**
     * Updates a friendship entity or throws error is it is not found
     * @param userName1 first ID
     * @param userName2 second ID
     */
    public void updateFriendship(String userName1, String userName2) {
        Friendship friendship = friendshipsRepo.findOne(new OrderedStringPair(userName1, userName2));
        if (friendship == null) {
            throw new ServiceException("Friendship does not exist!");
        }
        friendship.setFriendsFrom(LocalDateTime.now());
        friendshipsRepo.update(friendship);
        notifyObservers(new EntityChangedEvent(ChangeEventType.UPDATE, friendship));
    }

    /**
     * Removes a friendship
     * @param userName1, String
     * @param userName2, String
     * @throws ServiceException, if the friendship could not be deleted
     */
    public void removeFriendship(String userName1, String userName2) {
        Friendship deletedFriendship = friendshipsRepo.delete(new OrderedStringPair(userName1, userName2));
        if (deletedFriendship == null) {
            throw new ServiceException("Friendship does not exist!");
        }
        notifyObservers(new EntityChangedEvent(ChangeEventType.DELETE, deletedFriendship));
    }

    /**
     * @return A list with all the friendships
     */
    public Iterable<Friendship> getAllFriendships() {
        return friendshipsRepo.findAll();
    }

    /**
     * @return All communities from the community graph
     */
    public List<List<User>> getAllCommunities() {
        CommunityManager communityManager = new CommunityManager(getAllUsersWithFriends());
        return communityManager.getAllCommunities();
    }

    /**
     * @return The community with the longest path
     */
    public List<User> getMostSociableCommunity() {
        CommunityManager communityManager = new CommunityManager(getAllUsersWithFriends());
        return communityManager.findMostSociableCommunity();
    }

    /**
     * Builds a list with CommunityNodes, where every community node
     * represents a particular user and all of their friends
     * @return List with all CommunityNodes created from the saved users and saved friendships
     */
    private List<CommunityNode> getAllUsersWithFriends() {
        List<CommunityNode> nodes = new ArrayList<>();
        for (User user : usersRepo.findAll()) {
            nodes.add(getUserWithFriends(user.getId()));
        }
        return nodes;
    }

    /**
     * For a particular user, it builds a CommunityNode which consists of that user
     * and all of their friends
     * @param userId, String
     * @return CommunityNode made from user, of null is userId does not exist
     */
    private CommunityNode getUserWithFriends(String userId) {
        User user = usersRepo.findOne(userId);
        if (user == null) {
            return null;
        }
        CommunityNode communityNode = new CommunityNode(user);
        Iterable<Friendship> friendships = friendshipsRepo.findAll();
        for (Friendship friendship : friendships) {
            String userName1 = friendship.getId().getFirst();
            String userName2 = friendship.getId().getSecond();
            if (userName1.equals(userId)) {
                communityNode.addFriend(usersRepo.findOne(userName2));
            } else if (userName2.equals(userId)) {
                communityNode.addFriend(usersRepo.findOne(userName1));
            }
        }
        return communityNode;
    }

    public Iterable<User> getFriendsOfCurrentUser() {
        CommunityNode communityNode = getUserWithFriends(currentLoggedUser.getId());
        if (communityNode == null) throw new ServiceException("Current logged user is null!");
        return communityNode.getFriends();
    }

    /**
     * Can add a new friend request only if there is not a friendship between users
     * And last request does not exist or it is rejected.
     * @param otherUserName
     */
    public void addFriendRequest(String otherUserName) {
        User possibleUser = usersRepo.findOne(otherUserName);
        if (possibleUser == null) {
            throw new ServiceException("User does not exist!");
        }
        Friendship possibleFriendShip = friendshipsRepo.findOne(new OrderedStringPair(currentLoggedUser.getId(), otherUserName));
        if (possibleFriendShip != null) {
            throw new ServiceException("You are already friends with this user!");
        }
        Iterable<FriendRequest> friendRequests = friendRequestsRepo.findAll();
        Iterable<FriendRequest> filteredSentRequests = StreamSupport.stream(friendRequests.spliterator(), false)
                .filter(x -> x.getFromId().equals(currentLoggedUser.getId()) && x.getToId().equals(otherUserName))
                .sorted(new Comparator<FriendRequest>() {
                    @Override
                    public int compare(FriendRequest o1, FriendRequest o2) {
                        return o2.getTimeSent().compareTo(o1.getTimeSent());
                    }
                })
                .collect(Collectors.toList());
        Iterable<FriendRequest> filteredReceivedRequests = StreamSupport.stream(friendRequests.spliterator(), false)
                .filter(x -> x.getToId().equals(currentLoggedUser.getId()) && x.getFromId().equals(otherUserName))
                .sorted(new Comparator<FriendRequest>() {
                    @Override
                    public int compare(FriendRequest o1, FriendRequest o2) {
                        return o2.getTimeSent().compareTo(o1.getTimeSent());
                    }
                })
                .collect(Collectors.toList());

        if (!filteredSentRequests.iterator().hasNext() && !filteredReceivedRequests.iterator().hasNext()) {
            friendRequestsRepo.save(
                    new FriendRequest(currentLoggedUser.getId(),
                            otherUserName)
            );
        } else {
            FriendRequest lastSent = filteredSentRequests.iterator().hasNext() ? filteredSentRequests.iterator().next() : null;
            FriendRequest lastReceived = filteredReceivedRequests.iterator().hasNext() ? filteredReceivedRequests.iterator().next() : null;
            if (lastSent != null && lastSent.getStatus().equals(Status.PENDING)) {
                throw new ServiceException("There is already a pending friend request!");
            }
            if (lastReceived != null && lastReceived.getStatus().equals(Status.PENDING)) {
                //lastReceived.setStatus(Status.ACCEPTED);
                //friendRequestsRepo.update(lastReceived);
                //addFriendShip(currentLoggedUser.getId(), otherUserName);
                throw new ServiceException("You have a pending friend request from this user!");
            } else {
                FriendRequest newFriendRequest = new FriendRequest(currentLoggedUser.getId(), otherUserName);
                friendRequestsRepo.save(newFriendRequest);
                notifyObservers(new EntityChangedEvent(ChangeEventType.ADD, newFriendRequest));

            }
        }
    }

    public void updateFriendRequest(FriendRequest oldFriendRequest, Status newStatus) {
        oldFriendRequest.setStatus(newStatus);
        if (newStatus.equals(Status.ACCEPTED)) {
            addFriendShip(oldFriendRequest.getFromId(), oldFriendRequest.getToId());
        }
        friendRequestsRepo.update(oldFriendRequest);
        notifyObservers(new EntityChangedEvent(ChangeEventType.UPDATE, oldFriendRequest));
    }

    public Iterable<FriendRequest> getFriendRequest(Status status) {
        String currentUserName = currentLoggedUser.getId();
        Iterable<FriendRequest> allFriendRequests = friendRequestsRepo.findAll();
        return StreamSupport.stream(allFriendRequests.spliterator(), false)
                .filter(x -> x.getStatus().equals(status)
                        && (x.getFromId().equals(currentUserName) || x.getToId().equals(currentUserName)))
                .collect(Collectors.toList());
    }

    @Override
    public void addObserver(Observer<EntityChangedEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<EntityChangedEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(EntityChangedEvent t) {
        observers.forEach(x -> x.update(t));
    }
}

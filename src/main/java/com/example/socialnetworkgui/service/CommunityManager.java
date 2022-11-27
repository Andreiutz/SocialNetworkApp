package com.example.socialnetworkgui.service;


import com.example.socialnetworkgui.domain.CommunityNode;
import com.example.socialnetworkgui.domain.user.User;

import java.util.*;

/**
 * Handles with the nodes of the community graph
 */
public class CommunityManager {
    private Map<String, CommunityNode> nodes;

    public CommunityManager() {
        nodes = new HashMap<>();
    }

    /**
     * Constructor
     * @param allNodes, nodes from the graph
     */
    public CommunityManager(List<CommunityNode> allNodes) {
        nodes = new HashMap<>();
        for (CommunityNode node : allNodes) {
            addNode(node);
        }
    }

    /**
     * Add a new node to the graph
     * @param newNode, CommunityNode
     */
    public void addNode(CommunityNode newNode) {
        nodes.put(newNode.getUser().getId(), newNode);
    }

    /**
     * Makes a dfs visit in order to search for the current component from the graph
     * @param visitedNodes, keeps track of the nodes which have already been visited
     * @param currentNode, CommunityNode, the currentNode we are in the bfs algorithm
     * @param currentComponent, CommunityNodes from the current component are stored in this
     */
    private void dfsVisit(Set<String> visitedNodes, CommunityNode currentNode, List<User> currentComponent) {
        if (!visitedNodes.contains(currentNode.getUser().getId())) {
            visitedNodes.add(currentNode.getUser().getId());
            currentComponent.add(currentNode.getUser());
            for (User friend : currentNode.getFriends()) {
                dfsVisit(visitedNodes, nodes.get(friend.getId()), currentComponent);
            }
        }
    }

    /**
     * Builds all different communities from the graph
     * @return the list of the communities
     */
    public List<List<User>> getAllCommunities() {
        Set<String> visited = new HashSet<>();
        List<List<User>> allCommunities = new ArrayList<>();
        for (CommunityNode node : nodes.values()) {
            if (!visited.contains(node.getUser().getId())) {
                List<User> currentCommunity = new ArrayList<>();
                dfsVisit(visited, node, currentCommunity);
                allCommunities.add(currentCommunity);
            }
        }
        return allCommunities;
    }

    /**
     * Given a particular node, it finds the longest path which starts there
     * @param communityUser the current node of the search
     * @param visited keeps track of the visited users
     * @return the longest path starting from communityUser node
     */
    private int findLongestPath(User communityUser, Set<String> visited) {
        int longestPath = 0;
        visited.add(communityUser.getId());
        for (User friend : nodes.get(communityUser.getId()).getFriends()) {
            if (!visited.contains(friend.getId())) {
                longestPath = Math.max(longestPath, 1 + findLongestPath(friend, visited));
            }
        }
        visited.remove(communityUser.getId());
        return longestPath;
    }

    /**
     * Finds the longest path in a given community
     * @param community, represents a community
     * @return the longest path from the current community
     */
    private int findLongestPath(List<User> community) {
        int longestPath = 0;
        Set<String> visited = new HashSet<>();
        for (User communityUser : community) {
            longestPath =  Math.max(longestPath, findLongestPath(communityUser, visited));
        }
        return longestPath;
    }

    /**
     * Finds the most sociable community, meaning the community with the longest path
     * @return the most sociable community
     */
    public List<User> findMostSociableCommunity() {
        List<List<User>> allCommunities = getAllCommunities();
        List<User> mostSociableCommunity = null;
        int maxLength = -1;
        for (List<User> community : allCommunities) {
            int currentPathLength = findLongestPath(community);
            if (currentPathLength > maxLength) {
                maxLength = currentPathLength;
                mostSociableCommunity = community;
            }
        }
        return mostSociableCommunity;
    }



}

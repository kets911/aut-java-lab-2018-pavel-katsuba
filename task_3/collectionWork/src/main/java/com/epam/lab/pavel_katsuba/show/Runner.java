package com.epam.lab.pavel_katsuba.show;

import com.epam.lab.pavel_katsuba.collection.beans.Sex;
import com.epam.lab.pavel_katsuba.collection.beans.User;
import com.epam.lab.pavel_katsuba.collection.implementations.*;
import com.epam.lab.pavel_katsuba.collection.interfaces.*;

import java.util.Comparator;

public class Runner {
    private static List<Integer> mergeList = new ArrayList<>();
    private static List<User> userMergeList = new ArrayList<>();
    public static void main(String[] args) {
        mergeList.add(1);
        mergeList.add(6);
        mergeList.add(8);
        userMergeList.add(new User("Uncle", "uncle@r.com", Sex.MALE));
        userMergeList.add(new User("Sadi", "non@r.com", Sex.FEMALE));
        userMergeList.add(new User("John", "john@r.com", Sex.MALE));
        System.out.println("------------------ARRAY_LIST-----------------");
        List<Integer> arrayList = new ArrayList<>();
        showListWork(arrayList);
        System.out.println("------------------USERS_ARRAY_LIST-----------------");
        List<User> arrayListUsers = new ArrayList<>();
        showListWorkWithUsers(arrayListUsers);
        System.out.println("------------------LINKED_LIST-----------------");
        List<Integer> linked = new LinkedList<>();
        showListWork(linked);
        System.out.println("------------------USERS_LINKED_LIST-----------------");
        List<User> linkedListUsers = new LinkedList<>();
        showListWorkWithUsers(linkedListUsers);
        System.out.println("------------------ARRAY_STACK-----------------");
        Stack<Integer> arrayStack = new ArrayStack<>();
        showStackWork(arrayStack);
        System.out.println("------------------USERS_ARRAY_STACK-----------------");
        Stack<User> userArrayStack = new ArrayStack<>();
        showStackWorkWithUsers(userArrayStack);
        System.out.println("------------------LIST_STACK-----------------");
        Stack<Integer> listStack = new ListStack<>();
        showStackWork(listStack);
        System.out.println("------------------USERS_LIST_STACK-----------------");
        Stack<User> userListStack = new ListStack<>();
        showStackWorkWithUsers(userListStack);
        System.out.println("------------------ARRAY_QUEUE-----------------");
        Queue<Integer> arrayQueue = new ArrayQueue<>();
        showQueueWork(arrayQueue);
        System.out.println("------------------USERS_ARRAY_QUEUE-----------------");
        Queue<User> usersArrayQueue = new ArrayQueue<>();
        showQueueWorkWithUsers(usersArrayQueue);
        System.out.println("------------------LIST_QUEUE-----------------");
        Queue<Integer> listQueue = new ListQueue<>();
        showQueueWork(listQueue);
        System.out.println("------------------USERS_LIST_QUEUE-----------------");
        Queue<User> usersListQueue = new ListQueue<>();
        showQueueWorkWithUsers(usersListQueue);
        System.out.println("------------------ARRAY_MAP------------------");
        Map<String, Integer> arrayMap = new ArrayMap<>();
        showMapWork(arrayMap);
        System.out.println("------------------USERS_ARRAY_MAP------------------");
        Map<String, User> usersArrayMap = new ArrayMap<>();
        showMapWorkWithUsers(usersArrayMap);
        System.out.println("------------------LIST_MAP------------------");
        Map<String, Integer> listMap = new ListMap<>();
        showMapWork(listMap);
        System.out.println("------------------USERS_LIST_MAP------------------");
        Map<String, User> usersListMap = new ListMap<>();
        showMapWorkWithUsers(usersListMap);
    }
    private static void showListWork(List<Integer> list){
        list.add(1);
        list.add(4);
        list.add(8);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(null);
        list.addAll(new Integer[]{10, 12});
        System.out.println(list);
        System.out.println(list.size());
        System.out.println("find null " + list.find(null));
        System.out.println("find 55 " + list.find(55));
        list.trim();
        System.out.println("list after trim -> " + list);
        list.filterDifference(mergeList);
        System.out.println("list after filterDifference with mergeList -> " + list);
        System.out.println("get by 3 index -> " + list.get(3));
        System.out.println("set by 3 index -> " + list.set(3, 7));
        System.out.println(list);
        Iterator<Integer> iterator = list.getIterator();
        while (iterator.hasNext()){
            int element = iterator.getNext();
            if (element == 5){
                System.out.println("index addBefor - > " + iterator.addBefore(8));
                System.out.println("index addAfter - > " + iterator.addAfter(3));
            }
        }
        System.out.println(list);
        list.setMaxSize(6);
        System.out.println("list with maxSixe = 6 " + list);
        System.out.println("list to array -> " + list.toArray(new Integer[1]));
        list.clear();
        System.out.println("list after cleaning " + list);
    }
    private static void showListWorkWithUsers(List<User> users){
        users.add(new User("Arthur", "morgan@r.com", Sex.MALE));
        users.add(new User("Jane", "jane@r.com", Sex.FEMALE));
        users.add(null);
        users.addAll(new User[]{new User("Mika", "mika@r.com", Sex.MALE), new User("Datch", "datch@r.com", Sex.MALE)});
        users.addAll(userMergeList);
        System.out.println(users);
        users.trim();
        System.out.println("users after trim " + users);
        System.out.println("get 0 -> " + users.get(0));
        System.out.println("set 0 -> " + users.set(0, new User("John", "john@r.com", Sex.MALE)));
        System.out.println(users);
        System.out.println("remove Mika -> " + users.remove(users.find(new User("Mika", "mika@r.com", Sex.MALE))));
        for (User user : users.toArray(new User[2])){
            System.out.println(user);
        }
        System.out.println();
        users.filterMatches(userMergeList);
        System.out.println("users after filterMatches " + users);
        users.setMaxSize(1);
        System.out.println("users with maxSize = 1 " + users);
        users.clear();
        System.out.println("users after cleaning " + users);
    }
    private static void showStackWork(Stack<Integer> stack){
        System.out.println("is empty before pushing ? " + stack.isEmpty());
        stack.push(4);
        System.out.println("is empty after pushing ? " + stack.isEmpty());
        stack.pushAll(new Integer[]{5,7});
        stack.pushAll(mergeList);
        System.out.println(stack);
        System.out.println("peek returns " + stack.peek());
        System.out.println(stack);
        System.out.println("pop returns " + stack.pop());
        System.out.println(stack);
        System.out.println("search 5 returns " + stack.search(5));
        System.out.println("search 999 returns " + stack.search(999));
        Iterator<Integer> iterator = stack.getIterator();
        while (iterator.hasNext()){
            Integer element = iterator.getNext();
            if (element == 5){
                System.out.println("index addBefor - > " + iterator.addBefore(8));
                System.out.println("index addAfter - > " + iterator.addAfter(3));
            }
        }
        System.out.println(stack);
        System.out.println("size = " + stack.size());
        stack.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println(stack);
        stack.clear();
        System.out.println("size after cleaning = " + stack.size());
        System.out.println(stack);
    }
    private static void showStackWorkWithUsers(Stack<User> users){
        System.out.println("stack is empty before push? " + users.isEmpty());
        users.push(new User("Arthur", "morgan@r.com", Sex.MALE));
        users.push(new User("Jane", "jane@r.com", Sex.FEMALE));
        System.out.println("stack is empty after push? " + users.isEmpty());
        users.pushAll(new User[]{new User("Mika", "mika@r.com", Sex.MALE), new User("Datch", "datch@r.com", Sex.MALE)});
        users.pushAll(userMergeList);
        System.out.println(users);
        System.out.println("search Arthur -> " + users.search(new User("Arthur", "morgan@r.com", Sex.MALE)));
        System.out.println("peek -> " + users.peek());
        System.out.println("pop -> " + users.pop());
        System.out.println(users);
        System.out.println("size -> " + users.size());
        users.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("stack after sort " + users);
        users.clear();
        System.out.println("stack is empty after cleaning? " + users.isEmpty());
    }
    private static void showQueueWork(Queue<Integer> queue){
        System.out.println("queue is before pushing " + queue.isEmpty());
        queue.push(4);
        queue.push(5);
        queue.push(7);
        System.out.println("queue is after pushing " + queue.isEmpty());
        System.out.println("queue size = " + queue.size());
        System.out.println(queue);
        System.out.println("queue peek returns " + queue.peek());
        System.out.println(queue);
        System.out.println("queue pull returns " + queue.pull());
        System.out.println(queue);
        System.out.println("queue poll returns " + queue.poll());
        System.out.println(queue);
        System.out.println("queue remove returns " + queue.remove());
        System.out.println(queue);
        queue.pushAll(mergeList);
        System.out.println(queue);
        queue.pushAll(new Integer[]{12, 13});
        System.out.println(queue);
        Iterator<Integer> iterator = queue.getIterator();
        while (iterator.hasNext()){
            if (iterator.getNext() == 5){
                System.out.println("index addBefor 8- > " + iterator.addBefore(8));
                System.out.println("index addAfter 3- > " + iterator.addAfter(3));
            }
        }
        System.out.println(queue);
        System.out.println("search 12 returns " + queue.search(12));
        System.out.println("clear returns " + queue.clear());
        System.out.println(queue);
        System.out.println("queue is empty? " + queue.isEmpty());
    }
    private static void showQueueWorkWithUsers(Queue<User> users){
        System.out.println("queue is empty before push? " + users.isEmpty());
        users.push(new User("Arthur", "morgan@r.com", Sex.MALE));
        users.push(new User("Jane", "jane@r.com", Sex.FEMALE));
        System.out.println("queue is empty after push? " + users.isEmpty());
        users.pushAll(new User[]{new User("Mika", "mika@r.com", Sex.MALE), new User("Datch", "datch@r.com", Sex.MALE)});
        users.pushAll(userMergeList);
        System.out.println(users);
        System.out.println("search Arthur -> " + users.search(new User("Arthur", "morgan@r.com", Sex.MALE)));
        System.out.println("peek -> " + users.peek());
        System.out.println("poll -> " + users.poll());
        System.out.println(users);
        System.out.println("pull -> " + users.pull());
        System.out.println("remove -> " + users.remove());
        System.out.println(users);
        System.out.println("size -> " + users.size());
        users.clear();
        System.out.println("queue is empty after cleaning? " + users.isEmpty());
    }
    private static void showMapWork(Map<String, Integer> map){
        System.out.println("map is empty before set? " + map.isEmpty());
        map.set("1", 1);
        map.set("2", 2);
        map.set("3", 3);
        map.set("4", 4);
        map.set("5", 5);
        System.out.println("map is empty after set? " + map.isEmpty());
        System.out.println("map size is " + map.size());
        System.out.println(map);
        System.out.println("removed entity by key -> " + map.remove("5"));
        System.out.println("removed entity by entity from ArrayMap -> " + map.remove(new ArrayMap.ArrayMapEntity<>("4", 4)));
        System.out.println("removed entity by entity from ListMap -> " + map.remove(new ListMap.EntityListMap<>("3", 3)));
        System.out.println(map);
        System.out.println("keys -> " + map.getKeys());
        System.out.println("values -> " + map.getValues());
        System.out.println("entity with key 3 -> " + map.getEntity("3"));
        System.out.println("value with key 3 -> " + map.get("3"));
        System.out.println("contain 3? -> " + map.contains(3));
        System.out.println("contain 7? -> " + map.contains(7));
        System.out.println("clear -> " + map.clear());
        System.out.println("map is empty after cleaning? " + map.isEmpty());
        System.out.println("map size after cleaning is " + map.size());
        System.out.println(map);
    }
    private static void showMapWorkWithUsers(Map<String, User> users){
        System.out.println("map is empty befor set? " + users.isEmpty());
        users.set("Arthur", new User("Arthur", "morgan@r.com", Sex.MALE));
        users.set("Datch", new User("Datch", "datch@r.com", Sex.MALE));
        users.set("Mika", new User("Mika", "mika@r.com", Sex.MALE));
        users.set("John",new User("John", "john@r.com", Sex.MALE));
        System.out.println("map is empty after set? " + users.isEmpty());
        System.out.println(users);
        System.out.println("keys -> " + users.getKeys());
        System.out.println("values -> " + users.getValues());
        System.out.println("get Mika -> " + users.get("Mika"));
        System.out.println("getEntity Mika -> " + users.getEntity("Mika"));
        System.out.println("map contains Arthur -> " + users.contains(new User("Arthur", "morgan@r.com", Sex.MALE)));
        System.out.println("remove Mika by key -> " + users.remove("Mika"));
        System.out.println("remove Arthur by entity from ArrayMap -> " +
                users.remove(new Map.Entity<String, User>() {
                                 @Override
                                 public String getKey() {
                                     return "Arthur";
                                 }

                                 @Override
                                 public User getValue() {
                                     return new User("Arthur", "morgan@r.com", Sex.MALE);
                                 }
                             }));
        System.out.println(users);
        System.out.println("map contains Arthur -> " + users.contains(new User("Arthur", "morgan@r.com", Sex.MALE)));
        System.out.println("size -> " + users.size());
        System.out.println("clear -> " + users.clear());
        System.out.println(users);
    }
}

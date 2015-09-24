# Search Readme

Simple in-memory search implementation using a Hashmap, where
Hashmap<word:String, search_tuples:SortedSet<SearchTuple>>.
SearchTuple is {word:String, score:double, document_name:String}. 

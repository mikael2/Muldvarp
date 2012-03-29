package no.hials.muldvarp.deprecated;

///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package no.hials.muldvarp.video;
//
//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.EditText;
//import android.widget.ListView;
//import java.util.ArrayList;
//import no.hials.muldvarp.R;
//import no.hials.muldvarp.entities.Video;
//import no.hials.muldvarp.utility.ListViewAdapter;
//
///**
// * Fragment defining a list. The difference between this class and CustomListFragement is the version of Fragment that is extended, in
// * addition to other changes to make swiping possible.
// * 
// * @author johan
// */
//public class MyVideosFragment extends Fragment {
//    
//    //Global variables
//    String viewName = "";
//    public String fragmentName = "";
//    ListView listView;
//    ArrayList<Object> currentListItems;
//    
//    //Switches
//    public boolean enableFilter = true;
//    
//    //FilterText solution
//    private EditText filterText = null;
//    ListViewAdapter adapter;
//    
//    @Override
//    public void onCreate(Bundle savedStateInstance) {
//        super.onCreate(savedStateInstance);
//    }
//
//        
//    /**
//     * Creates the view when called.
//     *  
//     * @param inflater
//     * @param container
//     * @param savedInstanceState
//     * @return 
//     */
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        
//        
//        
//        //Set View and layout, using LayoutInflater to inflate layout form a XML-resource
//        //The XML is located in /layout/--XML
//        View returnFragmentView = inflater.inflate(R.layout.layout_listview, container, false);
//        
//         //Get ListView
//        listView = (ListView)returnFragmentView.findViewById(R.id.layoutlistview);  
//        
//        
//        //Set filterText and add listener
//        filterText = (EditText)returnFragmentView.findViewById(R.id.search_box);
//        filterText.addTextChangedListener(filterTextWatcher);
//               
//        
//        //Set onItemClickListener
//        //Defines what happens when an item in the list is "clicked"        
//        listView.setOnItemClickListener(new OnItemClickListener() {
//
//            public void onItemClick(AdapterView<?> arg0, View view, int itemPosition, long id) {
//                
//                if (viewName.equalsIgnoreCase("Courses")) {
//                    
//                    Intent newIntent = new Intent(view.getContext().getApplicationContext(), VideoCourseActivity.class);
//                    startActivityForResult(newIntent, 0);
//                    
//                    
//                } else {
//                
//                
//                Video selectedItem = (Video) currentListItems.get(itemPosition);
//                
//                
//                //Create new Intent along with data to be passed on
//                //Should be changed to only supply ID and let the VideoActivity take care of the rest
//                Intent newIntent = new Intent(view.getContext().getApplicationContext(), VideoActivity.class);
////                Intent newIntent = new Intent(view.getContext().getApplicationContext(), DSA.class);
//                newIntent.putExtra("videoID", selectedItem.getVideoID());
//                newIntent.putExtra("videoName", selectedItem.getItemName());
//                newIntent.putExtra("smallDetail", selectedItem.getSmallDetail());
//                newIntent.putExtra("itemDescription", selectedItem.getItemDescription());
//                newIntent.putExtra("videoURL", selectedItem.getVideoURL());
//                
//                //Start Activity
//                startActivityForResult(newIntent, 0);
//                
//                }
//            }
//        });
//                 
//        
//               
//        return returnFragmentView;
//        
//    }   
//    
//    
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        //setUserVisibleHint(true);
//    }
//    
//    /**
//     * Set the name of the view.
//     * 
//     * @param tabViewName String value of the view's name.
//     */
//    public void setViewName(String viewName) {
//        
//        this.viewName = viewName;
//    }
//    
//    
//    
//    public void getAdapter(ArrayList itemList) {
//        
//        currentListItems = itemList;
//                        
//        adapter = new ListViewAdapter(this.getActivity().getApplicationContext(),
//                                                              R.layout.course_list_item,
//                                                              R.id.courselisttext,
//                                                              itemList, true);        
//        
//        //Set ListViewAdapter
//        listView.setAdapter(adapter);  
//        
//    }
//    
//    
//
//    /**
//     * Function which returns the fragment name.
//     * 
//     * @return Returns the fragment name.
//     */
//    public String getFragmentName() {
//        return fragmentName;
//    }
//
//    
//    /**
//     * Function which sets the fragment name.
//     * 
//     * @param fragmentName The name of the fragment to be displayed in the title.
//     */
//    public void setFragmentName(String fragmentName) {
//        this.fragmentName = fragmentName;
//    }
//    
//    /**
//     * TextWatcher used to update the list.
//     */
//    private TextWatcher filterTextWatcher = new TextWatcher() {
//
//        /**
//         * Defines what happens after a text change.
//         */
//        public void afterTextChanged(Editable s) {
//        }
//
//        /**
//         * Name says it all
//         */
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        }
//
//        /**
//         * Defines what should happen when the text changes.
//         */
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            
//            //Set adapter for use by the Filter function
//            adapter = (ListViewAdapter) listView.getAdapter();
//            if(adapter != null){
//                adapter.filter(s);
//            }
//        }
//    };
//    
//
//}

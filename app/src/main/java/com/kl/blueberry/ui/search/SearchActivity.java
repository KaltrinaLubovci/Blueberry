package com.kl.blueberry.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.kl.blueberry.R;
import com.kl.blueberry.adapters.search.SearchAdapter;
import com.kl.blueberry.api.ApiService;
import com.kl.blueberry.databinding.SearchActivityBinding;
import com.kl.blueberry.model.playlist.PlaylistTracksDataResponse;
import com.kl.blueberry.model.search_music.SearchMusicDataResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import static com.kl.blueberry.utils.Usage.showToast;

import dagger.android.AndroidInjection;

public class SearchActivity extends AppCompatActivity {

    SearchActivityBinding binding;
    ArrayList<SearchMusicDataResponse> resultArrList = new ArrayList<>();
    RecyclerView rvResults;
    String artistName;
    SearchAdapter searchAdapter;
    @Inject
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.search_activity);
        binding.setViewModel(new SearchViewModel());

//        binding.getViewModel().getMainPlaylist(apiService);
        searchAdapter = new SearchAdapter(SearchActivity.this, new ArrayList<>());
        rvResults = binding.rvSearchResult;
        rvResults.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        rvResults.setAdapter(searchAdapter);
        observeViewModel();
        onClicks();
    }

    private void observeViewModel(){

        binding.getViewModel().musicResponseList.observe(this, new Observer<List<SearchMusicDataResponse>>() {

            @Override
            public void onChanged(List<SearchMusicDataResponse> searchMusicDataResponses) {
                resultArrList.clear();
                resultArrList.addAll(searchMusicDataResponses);
                searchAdapter.setSearchData(resultArrList);
                System.out.println("trackssss sizeeee " + searchMusicDataResponses.size());
            }
        });

    }

    private void showSnackBar(){
        Snackbar snackbar = Snackbar.make(binding.coordinatorLayout, "Please wait ...", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void onClicks(){
        binding.ivBack.setOnClickListener(onClick -> finish());

        binding.ivSearchNow.setOnClickListener(onClick -> {

            if (validateData()){
                showSnackBar();
                binding.getViewModel().search(SearchActivity.this, apiService, artistName);
            }
            hideKeyboard(binding.etSearch);
        });
    }

    private void hideKeyboard(EditText editText) {
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(
                    editText.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN
            );
        }
    }


    private Boolean validateData(){
      artistName = binding.etSearch.getEditableText().toString();
      if (artistName.isEmpty()){
          showToast(this, "Search field cannot be empty!");
          return false;
      } else {
          return true;
      }
    }
    @Override
    protected void onPause() {
        super.onPause();
        searchAdapter.closeAllPlayers();
    }
}

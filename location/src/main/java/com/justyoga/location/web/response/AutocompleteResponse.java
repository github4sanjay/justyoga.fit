package com.justyoga.location.web.response;

import com.justyoga.location.web.dto.AutocompleteDTO;
import java.io.Serializable;
import java.util.List;

public class AutocompleteResponse implements Serializable {
    private List<AutocompleteDTO> autocompleteList;
    private String sessionId;

    public AutocompleteResponse(List<AutocompleteDTO> autocompleteList, String sessionId) {
        this.autocompleteList = autocompleteList;
        this.sessionId = sessionId;
    }

    public AutocompleteResponse() {}

    public List<AutocompleteDTO> getAutocompleteList() {
        return autocompleteList;
    }

    public void setAutocompleteList(List<AutocompleteDTO> autocompleteList) {
        this.autocompleteList = autocompleteList;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

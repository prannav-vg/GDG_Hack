package com.example.gdg_hack.ui.alerts

object IncidentStore {

    private val incidents = mutableListOf<Incident>()

    fun log(incident: Incident) {
        incidents.add(0, incident)
    }

    fun getHighRisk(): List<Incident> {
        return incidents
    }

    fun clear() {
        incidents.clear()
    }
}


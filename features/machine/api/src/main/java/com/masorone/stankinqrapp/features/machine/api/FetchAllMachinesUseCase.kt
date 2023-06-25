package com.masorone.stankinqrapp.features.machine.api

import com.masorone.stankinqrapp.core.main.SuspendFetchAll
import com.masorone.stankinqrapp.features.machine.api.model.Machine

interface FetchAllMachinesUseCase : SuspendFetchAll<List<Machine>>

package com.masorone.stankinqrapp.features.machine.api

import com.masorone.stankinqrapp.core.SuspendFetchAll
import com.masorone.stankinqrapp.features.machine.api.model.Machines

interface FetchAllAllMachinesUseCase : SuspendFetchAll<Machines>

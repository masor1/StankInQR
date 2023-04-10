package com.masorone.stankinqrapp.features.machine.api

import com.masorone.stankinqrapp.core.SuspendFetchById
import com.masorone.stankinqrapp.features.machine.api.model.Machine

interface FetchMachineByIdUseCase : SuspendFetchById<String, Machine>
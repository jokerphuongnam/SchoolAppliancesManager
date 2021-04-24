package com.example.schoolappliancesmanager.di;

import com.example.schoolappliancesmanager.model.usecase.AddApplianceUseCase;
import com.example.schoolappliancesmanager.model.usecase.AddRoomUseCase;
import com.example.schoolappliancesmanager.model.usecase.BorrowUseCase;
import com.example.schoolappliancesmanager.model.usecase.DefaultAddApplianceUseCaseImpl;
import com.example.schoolappliancesmanager.model.usecase.DefaultAddRoomUseCaseImpl;
import com.example.schoolappliancesmanager.model.usecase.DefaultBorrowUseCaseImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class AppBindsModules {
    @Binds
    abstract AddRoomUseCase getAddRoomUseCase(DefaultAddRoomUseCaseImpl useCase);
    @Binds
    abstract AddApplianceUseCase getAddApplianceUseCase(DefaultAddApplianceUseCaseImpl useCase);
    @Binds
    abstract BorrowUseCase getBorrowUseCase(DefaultBorrowUseCaseImpl useCase);
}

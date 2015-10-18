CREATE TABLE [cukes].[StepResults]
(
	[Id] BIGINT NOT NULL PRIMARY KEY IDENTITY(1,1), 
    [RunID] BIGINT NULL, 
    [FeatureID] BIGINT NULL, 
    [ScenarioID] BIGINT NULL, 
    [Keyword] VARCHAR(5) NULL, 
    [Value] VARCHAR(100) NULL, 
    [TableSteps] TEXT NULL, 
    [Matches] VARCHAR(100) NULL, 
    [Status] VARCHAR(10) NULL, 
    [ErrorMessage] VARCHAR(500) NULL, 
    [Duration] NUMERIC(10, 4) NULL, 
    [StartAt] DATETIME NULL, 
    [EndAt] DATETIME NULL, 
    CONSTRAINT [FK_StepResults_Runs] FOREIGN KEY ([Id]) REFERENCES [cukes].[Runs]([Id]), 
    CONSTRAINT [FK_StepResults_Features] FOREIGN KEY ([Id]) REFERENCES [cukes].[Features]([Id]), 
    CONSTRAINT [FK_StepResults_Scenarios] FOREIGN KEY ([Id]) REFERENCES [cukes].[Scenarios]([Id])
)

GO

CREATE INDEX [IX_StepResults_ID] ON [cukes].[StepResults] ([Id])

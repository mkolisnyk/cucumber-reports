CREATE TABLE [cukes].[Features]
(
    [Id] BIGINT NOT NULL PRIMARY KEY IDENTITY(1,1), 
    [feature_id] [varchar](100) NULL,
	[Description] [varchar](500) NULL,
	[Name] [varchar](100) NULL,
	[Version] [bigint] NOT NULL,
	[ValidFrom] [datetime] NULL,
	[ValidTo] [datetime] NULL, 
)


GO

CREATE INDEX [IX_FEATURES_ID] ON [cukes].[Features] ([Id])
